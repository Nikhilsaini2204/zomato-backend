package com.example.restaurants.v1.dish.repository;

import com.example.restaurants.v1.dish.entity.DishEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends MongoRepository<DishEntity, ObjectId> {
  List<DishEntity> findByRestaurantId(ObjectId id);

  List<DishEntity> findByCategoryId(ObjectId categoryId);

  List<DishEntity> findByNameContainingIgnoreCase(String name);

  List<DishEntity> findByRestaurantIdAndName(ObjectId restaurantId, String dishName);

  List<DishEntity> findByRestaurantIdAndCategoryId(ObjectId restaurantId, ObjectId categoryId);

}
