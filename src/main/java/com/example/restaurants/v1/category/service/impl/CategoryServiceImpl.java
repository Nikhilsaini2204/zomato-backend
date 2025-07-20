package com.example.restaurants.v1.category.service.impl;


import com.example.restaurants.v1.category.entity.CategoryEntity;
import com.example.restaurants.v1.category.enums.Types;
import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.category.repository.CategoryRepository;
import com.example.restaurants.v1.category.service.CategoryService;
import com.example.restaurants.v1.dish.entity.DishEntity;
import com.example.restaurants.v1.dish.model.DishModel;
import com.example.restaurants.v1.dish.repository.DishRepository;
import com.example.restaurants.v1.restaurant.entity.RestaurantEntity;
import com.example.restaurants.v1.restaurant.repository.RestaurantRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private DishRepository dishRepository;
  //if creating a standard id user shd be zomato admin - validation
  @Override
  public CategoryModel createCategorySTD(CategoryModel categoryModel) {
    Optional<CategoryEntity> existing = categoryRepository
        .findByRestaurantIdIsNullAndNameIgnoreCase(categoryModel.getName());
    if (existing.isPresent()) {
      throw new RuntimeException("Standard category '" + categoryModel.getName() + "' already exists.");
    }
    CategoryEntity entity = new CategoryEntity(categoryModel);
    entity.setRestaurantId(null);
    CategoryEntity saved = categoryRepository.save(entity);
    return new CategoryModel(saved);
  }

  @Override
  public CategoryModel createCategory(String restaurantId, CategoryModel categoryModel) {
    ObjectId restId = new ObjectId(restaurantId);
    Optional<CategoryEntity>
        existing = categoryRepository.findByRestaurantIdAndNameIgnoreCase(restId, categoryModel.getName());
    if (existing.isPresent()) {
      throw new RuntimeException("Category '" + categoryModel.getName() + "' already exists for this restaurant.");
    }
    CategoryEntity entity = new CategoryEntity(categoryModel);
    entity.setRestaurantId(restId);
    CategoryEntity savedEntity = categoryRepository.save(entity);
    return new CategoryModel(savedEntity);
  }

  @Override
  public CategoryModel getById(String categoryId) {
     CategoryEntity entity = categoryRepository.findById(new ObjectId(categoryId))
         .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));;
    return new CategoryModel(entity) ;
  }

  @Override
  public List<CategoryModel> getStandardCategory() {
    List<CategoryEntity> categories = categoryRepository.findStandardCategories();
    return categories.stream()
        .map(CategoryModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<CategoryModel> getCategoryByTypeAndRestaurantID( String restaurantId) {
    Types type = Types.CUSTOM;
    List<CategoryEntity> categories = categoryRepository.findByTypeAndRestaurantId(type ,new ObjectId(restaurantId));
    return categories.stream()
        .map(CategoryModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<CategoryModel> getByRestaurantName(String restaurantName) {
    RestaurantEntity restaurant = restaurantRepository.findByNameIgnoreCase(restaurantName);
    if (restaurant == null) {
      throw new RuntimeException("Restaurant not found: " + restaurantName);
    }

    List<CategoryEntity> categories = categoryRepository.findByRestaurantId(restaurant.getId());
    return categories.stream()
        .map(CategoryModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<DishModel> getDishesByRestaurantAndCategory(String restaurantName,
      String categoryName) {
    RestaurantEntity restaurantEntity = restaurantRepository.findByName(restaurantName);
    CategoryEntity categoryEntity  = categoryRepository.findByName(categoryName);
    List<DishEntity> ls = dishRepository.findByRestaurantIdAndCategoryId(restaurantEntity.getId() , categoryEntity.getId());
    return ls.stream()
        .map(DishModel::new)
        .collect(Collectors.toList());
  }



}
