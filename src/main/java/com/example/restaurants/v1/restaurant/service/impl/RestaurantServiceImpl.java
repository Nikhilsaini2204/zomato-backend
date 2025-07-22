package com.example.restaurants.v1.restaurant.service.impl;

import com.example.restaurants.v1.restaurant.entity.RestaurantEntity;
import com.example.restaurants.v1.restaurant.enums.Request;
import com.example.restaurants.v1.restaurant.model.RestaurantModel;
import com.example.restaurants.v1.restaurant.repository.RestaurantRepository;
import com.example.restaurants.v1.restaurant.service.RestaurantService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Override
  public RestaurantModel createRestaurant(RestaurantModel restaurantModel) {
    if (restaurantRepository.existsByName(restaurantModel.getName())) {
      throw new RuntimeException("Restaurant is present");
    }

    RestaurantEntity restaurantEntity = new RestaurantEntity();
    restaurantEntity.setName(restaurantModel.getName());
    restaurantEntity.setTypeCuisine(restaurantModel.getTypeCuisine());
    restaurantEntity.setOpeningHour(restaurantModel.getOpeningHour());
    restaurantEntity.setClosingHour(restaurantModel.getClosingHour());
    restaurantEntity.setPhoneNumber(restaurantModel.getPhoneNumber());
    restaurantEntity.setOwnerId(restaurantModel.getOwnerId());
    restaurantEntity.setAddress(restaurantModel.getAddress());
    restaurantEntity.setRestaurantImage(restaurantModel.getRestaurantImage());
    restaurantEntity.setTime(restaurantModel.getTime());
    restaurantEntity.setRating(0.0f);
    restaurantEntity.setReviews(List.of());
    restaurantEntity.setAvailable(true);
    restaurantEntity.setRequest(Request.PENDING);
    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);
    return new RestaurantModel(saved);
  }

  public List<RestaurantModel> getRestaurantByFilter(String name, String typeCuisine,
      Boolean isAvailable) {
    List<RestaurantEntity> restaurant;
    List<RestaurantModel> restaurantModels = new ArrayList<>();
    if (name != null && typeCuisine != null && isAvailable != null) {
      restaurant =
          restaurantRepository.findByNameContainingIgnoreCaseAndTypeCuisineIgnoreCaseAndIsAvailable(
              name, typeCuisine, isAvailable);
    } else if (name != null && typeCuisine != null) {
      restaurant = restaurantRepository.findByNameContainingIgnoreCase(name).stream()
          .filter(r -> r.getTypeCuisine().equalsIgnoreCase(typeCuisine)).toList();
    } else if (name != null && isAvailable != null) {
      restaurant = restaurantRepository.findByNameContainingIgnoreCase(name).stream()
          .filter(r -> r.isAvailable() == isAvailable).toList();
    } else if (typeCuisine != null && isAvailable != null) {
      restaurant = restaurantRepository.findByTypeCuisineIgnoreCase(typeCuisine).stream()
          .filter(r -> r.isAvailable() == isAvailable).toList();
    } else if (name != null) {
      restaurant = restaurantRepository.findByNameContainingIgnoreCase(name);
    } else if (typeCuisine != null) {
      restaurant = restaurantRepository.findByTypeCuisineIgnoreCase(typeCuisine);
    } else if (isAvailable != null) {
      restaurant = restaurantRepository.findByIsAvailable(isAvailable);
    } else {
      restaurant = restaurantRepository.findByRequest(Request.APPROVED);
    }
    for (RestaurantEntity restaurants : restaurant) {
      RestaurantModel restaurantModel = new RestaurantModel();
      restaurantModel.setName(restaurants.getName());
      restaurantModel.setTypeCuisine(restaurants.getTypeCuisine());
      restaurantModel.setOpeningHour(restaurants.getOpeningHour());
      restaurantModel.setClosingHour(restaurants.getClosingHour());
      restaurantModel.setPhoneNumber(restaurants.getPhoneNumber());
      restaurantModel.setAddress(restaurants.getAddress());
      restaurantModel.setRestaurantImage(restaurants.getRestaurantImage());
      restaurantModel.setOwnerId(restaurants.getOwnerId());
      restaurantModels.add(restaurantModel);
    }

    return restaurantModels;
  }

  public RestaurantModel getRestaurantById(String restaurantId) {
    RestaurantEntity restaurant =
        restaurantRepository.findById(new ObjectId(restaurantId)).orElseThrow(()->new RuntimeException("Restaurant not found"));
    return new RestaurantModel(restaurant);
  }

  public String getDashboard(String restaurantId) {
    return "Dashboard";
  }

  public String getAnalytics(String restaurantId) {
    return "Analytics";
  }

  @Override
  public RestaurantModel approveRequestStatusByName(String name) {
    RestaurantEntity restaurantEntity = restaurantRepository.findByName(name);
    if (restaurantEntity == null) {
      throw new RuntimeException("Restaurant with name '" + name + "' not found.");
    }

    restaurantEntity.setRequest(Request.APPROVED);
    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);
    return new RestaurantModel(saved);
  }

  @Override
  public RestaurantModel rejectRequestStatusByName(String name) {
    RestaurantEntity restaurantEntity = restaurantRepository.findByName(name);
    if (restaurantEntity == null) {
      throw new RuntimeException("Restaurant with name '" + name + "' not found.");
    }

    restaurantEntity.setRequest(Request.REJECTED);
    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);
    return new RestaurantModel(saved);
  }

  public List<RestaurantModel> getPendingRequests() {
    List<RestaurantEntity> ls = restaurantRepository.findByRequest(Request.PENDING);
    return restaurantRepository.findByRequest(Request.PENDING).stream().map(RestaurantModel::new)
        .collect(Collectors.toList());
  }

  public RestaurantModel getRestaurantByName(String name) {
    RestaurantEntity restaurant = restaurantRepository.findByName(name);
    return new RestaurantModel(restaurant.getName(), restaurant.getTypeCuisine(),
        restaurant.getOpeningHour(), restaurant.getClosingHour(), restaurant.getPhoneNumber(),
        restaurant.getAddress(), restaurant.getRestaurantImage(), restaurant.isAvailable(),
        restaurant.getOwnerId());
  }

  public RestaurantModel getRestaurantByOwner(String ownerId) {
    Optional<RestaurantEntity> restaurant = restaurantRepository.findByOwnerId(ownerId);
    return restaurant.map(RestaurantModel::new)
        .orElseThrow(() -> new RuntimeException("No restaurant found for owner"));
  }

}

