package com.example.restaurants.v1.restaurant.service;

import com.example.restaurants.v1.restaurant.model.RestaurantModel;

import java.util.List;

public interface RestaurantService {
  RestaurantModel createRestaurant(RestaurantModel restaurantModel);

  List<RestaurantModel> getRestaurantByFilter(String name, String typeCuisine, Boolean isAvailable);

  RestaurantModel getRestaurantById(String restaurantId);

  String getDashboard(String restaurantId);

  String getAnalytics(String restaurantId);

  RestaurantModel approveRequestStatusByName(String name);

  RestaurantModel rejectRequestStatusByName(String name);

  List<RestaurantModel> getPendingRequests();

  RestaurantModel getRestaurantByName(String name);

  RestaurantModel getRestaurantByOwner(String ownerId);
}
