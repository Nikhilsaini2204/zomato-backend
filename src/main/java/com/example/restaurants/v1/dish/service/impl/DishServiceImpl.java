package com.example.restaurants.v1.dish.service.impl;

import com.example.restaurants.v1.category.entity.CategoryEntity;
import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.category.repository.CategoryRepository;
import com.example.restaurants.v1.dish.entity.DishEntity;
import com.example.restaurants.v1.dish.model.DishModel;
import com.example.restaurants.v1.dish.repository.DishRepository;
import com.example.restaurants.v1.dish.service.DishService;
import com.example.restaurants.v1.inventory.service.InventoryService;
import com.example.restaurants.v1.restaurant.entity.RestaurantEntity;
import com.example.restaurants.v1.restaurant.model.RestaurantModel;
import com.example.restaurants.v1.restaurant.repository.RestaurantRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private InventoryService inventoryService;




  @Override
  public DishModel addDish(DishModel dishModel, String restaurantId, String categoryId) {
    DishEntity dish = new DishEntity(dishModel);
    dish.setReview(List.of());
    dish.setRating(0.0f);
    dish.setRestaurantId(new ObjectId(restaurantId));
    dish.setCategoryId(new ObjectId(categoryId));
    DishEntity savedEntity = dishRepository.save(dish);
    inventoryService.createInventory(savedEntity.getId().toHexString(), dishModel.getDailyCapacity());
    return new DishModel(savedEntity);
  }

  @Override
  public DishModel updateDish(String id, DishModel dishModel) {
    DishEntity existingDish = dishRepository.findById(new ObjectId(id))
        .orElseThrow(() -> new RuntimeException("Dish not found"));
    existingDish.setName(dishModel.getName());
    existingDish.setAvailable(dishModel.isAvailable());
    existingDish.setDescription(dishModel.getDescription());
    existingDish.setPrepTime(dishModel.getPrepTime());
    existingDish.setDailyCapacity(dishModel.getDailyCapacity());
    existingDish.setPrice(dishModel.getPrice());
    existingDish.setImage(dishModel.getImage());
    dishRepository.save(existingDish);
    return new DishModel(existingDish);
  }

  @Override
  public DishModel getDishById(String id) {
    DishEntity dish = dishRepository.findById(new ObjectId(id))
        .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + id));
    return new DishModel(dish);
  }

  @Override
  public List<DishModel> getDishByRestaurantId(String restaurantId) {
    List<DishEntity> entities = dishRepository.findByRestaurantId(new ObjectId(restaurantId));
    return entities.stream()
        .map(DishModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<DishModel> getDishByCategoryId(String categoryId) {
    List<DishEntity> entities = dishRepository.findByCategoryId(new ObjectId(categoryId));
    return entities.stream()
        .map(DishModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public DishModel deleteByDishId(String id) {
    DishEntity dish = dishRepository.findById(new ObjectId(id))
        .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + id));
    DishModel deletedDish = new DishModel(dish);
    dishRepository.delete(dish);
    inventoryService.deleteByDishId(id);
    return deletedDish;
  }
//  @Override
//  public List<DishModel> getDishesByRestaurantName(String restaurantName) {
//    RestaurantEntity restaurant = restaurantRepository.findByName(restaurantName);
//    List<DishEntity> dishes = dishRepository.findByRestaurantId(restaurant.getId());
//    return dishes.stream().map(DishModel::new).collect(Collectors.toList());
//  }

  @Override
  public List<DishModel> getDishesByRestaurantName(String restaurantName, String dishName) {
    RestaurantEntity restaurant = restaurantRepository.findByName(restaurantName);

    if (restaurant==null) {
      throw new RuntimeException("Restaurant not found with name: " + restaurantName);
    }

    List<DishEntity> dishes;
    if (dishName != null && !dishName.isBlank()) {
        dishes = dishRepository.findByRestaurantIdAndName(restaurant.getId(), dishName);
      } else {
        dishes = dishRepository.findByRestaurantId(restaurant.getId());
      }
     return dishes.stream().map(DishModel::new).collect(Collectors.toList());
  }


  @Override
  public List<DishModel> getDishesByName(String name) {
    List<DishEntity> dishes = dishRepository.findByNameContainingIgnoreCase(name);
    return dishes.stream().map(DishModel::new).collect(Collectors.toList());
  }

  @Override
  public List<DishModel> getDishesByCategoryName(String categoryName)
  {
    CategoryEntity entity = categoryRepository.findByName(categoryName);
    List<DishEntity> ls = dishRepository.findByCategoryId(entity.getId());
    return ls.stream()
        .map(DishModel::new)
        .collect(Collectors.toList());
  }

  public List<RestaurantModel> getRestaurantsByCategoryName(String categoryName) {
    CategoryEntity category = categoryRepository.findByName(categoryName);
    if (category == null) {
      throw new RuntimeException("Category not found: " + categoryName);
    }

    List<DishEntity> dishes = dishRepository.findByCategoryId(category.getId());

    Set<ObjectId> restaurantIds = dishes.stream()
        .map(DishEntity::getRestaurantId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());

    List<RestaurantEntity> restaurants = restaurantRepository.findByIdIn(restaurantIds);

    return restaurants.stream()
        .map(entity -> new RestaurantModel(
            entity.getName(),
            entity.getTypeCuisine(),
            entity.getOwnerId(),
            entity.getOpeningHour(),
            entity.getClosingHour(),
            entity.getPhoneNumber(),
            entity.isAvailable(),
            entity.getAddress(),
            entity.getRestaurantImage(),
            entity.getRating(),
            entity.getTime(),
            entity.getRequest()
        ))
        .collect(Collectors.toList());
  }
  @Override
  public List<CategoryModel> getCategoriesByRestaurantName(String restaurantName) {
    RestaurantEntity restaurant = restaurantRepository.findByNameIgnoreCase(restaurantName);
    if (restaurant == null) {
      throw new RuntimeException("Restaurant not found with name: " + restaurantName);
    }
    ObjectId restaurantId = restaurant.getId();
    List<CategoryEntity> specificCategories = categoryRepository.findByRestaurantId(restaurantId);
    List<CategoryEntity> standardCategories = categoryRepository.findByType("STANDARD");
    List<CategoryEntity> allCategories = new ArrayList<>();
    allCategories.addAll(specificCategories);
    allCategories.addAll(standardCategories);

    return allCategories.stream()
        .map(CategoryModel::new)
        .collect(Collectors.toList());
  }

}
