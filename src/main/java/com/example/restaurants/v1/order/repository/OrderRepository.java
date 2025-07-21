package com.example.restaurants.v1.order.repository;

import com.example.restaurants.v1.order.entity.OrderEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, ObjectId> {
  List<OrderEntity> findByUserId(String userId);

  List<OrderEntity> findByRestaurantId(ObjectId restaurantId);
}
