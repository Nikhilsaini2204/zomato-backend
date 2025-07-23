package com.example.restaurants.v1.order.repository;

import com.example.restaurants.v1.order.entity.OrderEntity;
import com.example.restaurants.v1.order.enums.OrderStatus;
import org.bson.types.ObjectId;
import org.hibernate.query.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, ObjectId> {
  List<OrderEntity> findByUserId(String userId);
  List<OrderEntity> findByStatusNotIn(List<OrderStatus> orderStatuses);
  List<OrderEntity> findByRestaurantId(ObjectId restaurantId);
}
