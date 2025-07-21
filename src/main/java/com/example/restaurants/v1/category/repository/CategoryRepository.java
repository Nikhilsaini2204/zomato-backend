package com.example.restaurants.v1.category.repository;

import com.example.restaurants.v1.category.entity.CategoryEntity;
import com.example.restaurants.v1.category.enums.Types;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, ObjectId > {
//  CategoryEntity findByNameAndRestaurantId(String name, ObjectId restaurantId);

  @Query("{ 'type': 'STANDARD' }")
  List<CategoryEntity> findStandardCategories();
  List<CategoryEntity> findByTypeAndRestaurantId(Types type, ObjectId restaurantId);
  List<CategoryEntity> findByRestaurantId(ObjectId restaurantId);
  CategoryEntity findByName(String name);
  Optional<CategoryEntity> findByRestaurantIdAndNameIgnoreCase(ObjectId restaurantId, String name);
  Optional<CategoryEntity> findByRestaurantIdIsNullAndNameIgnoreCase(String name);
  List<CategoryEntity> findByType(String type); // where type is "STANDARD"
  CategoryEntity findByNameIgnoreCase(String name);
}
