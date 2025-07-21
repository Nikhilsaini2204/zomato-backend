package com.example.restaurants.v1.category.service;


import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.dish.model.DishModel;

import java.util.List;

public interface CategoryService {
  CategoryModel createCategory(String restaurantId, CategoryModel categoryModel);

  CategoryModel getById(String categoryId);

  List<CategoryModel> getStandardCategory();

  List<CategoryModel> getCategoryByTypeAndRestaurantID(String restaurantId);

  List<CategoryModel> getByRestaurantName(String restaurantName);

  List<DishModel> getDishesByRestaurantAndCategory(String restaurantName, String categoryName);

  CategoryModel createCategorySTD(CategoryModel categoryModel);

}
