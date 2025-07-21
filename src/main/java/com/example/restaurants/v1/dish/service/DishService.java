package com.example.restaurants.v1.dish.service;

import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.dish.model.DishModel;
import com.example.restaurants.v1.restaurant.model.RestaurantModel;

import java.util.List;

public interface DishService {
  DishModel addDish(DishModel dishModel, String restaurantId, String categoryId);

  DishModel updateDish(String dishID, DishModel dishModel);

  DishModel getDishById(String id);

  List<DishModel> getDishByRestaurantId(String restaurantId);

  List<DishModel> getDishByCategoryId(String categoryId);

  DishModel deleteByDishId(String id);

  public List<DishModel> getDishesByRestaurantName(String restaurantName, String dishName);

  //  List<DishModel> getByCategoryAndRestaurantId(String restaurantId , String categoryId);
  List<DishModel> getDishesByName(String name);

  List<DishModel> getDishesByCategoryName(String categoryName);

  List<RestaurantModel> getRestaurantsByCategoryName(String categoryName);

  List<CategoryModel> getCategoriesByRestaurantName(String restaurantName);
}
