package com.example.restaurants.v1.order.service;


import com.example.restaurants.v1.order.model.OrderModel;

import java.util.List;

public interface OrderService {
  OrderModel createOrder(OrderModel orderModel);
  OrderModel getById(String OrderId);
  OrderModel markByOrderID(String OrderId);
  OrderModel cancelByOrderId(String OrderId);
  List<OrderModel> findByUserId(String userId);
  List<OrderModel> findByRestaurantId(String restaurantId);
}
