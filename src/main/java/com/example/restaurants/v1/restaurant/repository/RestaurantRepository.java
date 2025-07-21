package com.example.restaurants.v1.restaurant.repository;

import com.example.restaurants.v1.restaurant.entity.RestaurantEntity;
import com.example.restaurants.v1.restaurant.enums.Request;
import com.example.restaurants.v1.restaurant.model.RestaurantModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends MongoRepository<RestaurantEntity, ObjectId> {
  List<RestaurantEntity> findByNameContainingIgnoreCase(String name);

  List<RestaurantEntity> findByTypeCuisineIgnoreCase(String typeCuisine);

  List<RestaurantEntity> findByIsAvailable(boolean isAvailable);

  List<RestaurantEntity> findByNameContainingIgnoreCaseAndTypeCuisineIgnoreCaseAndIsAvailable(
      String name, String typeCuisine, boolean isAvailable);

  Optional<RestaurantEntity> findById(ObjectId restaurantId);

  boolean existsByName(String name);

  RestaurantEntity findByName(String name);

  List<RestaurantEntity> findByIdIn(Collection<ObjectId> ids);

  RestaurantEntity findByNameIgnoreCase(String restaurantName);

  List<RestaurantEntity> findByRequest(Request request);

  Optional<RestaurantEntity> findByOwnerId(String ownerId);

}
