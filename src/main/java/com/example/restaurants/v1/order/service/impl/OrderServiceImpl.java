package com.example.restaurants.v1.order.service.impl;

import com.example.restaurants.v1.order.entity.OrderEntity;
import com.example.restaurants.v1.order.enums.OrderStatus;
import com.example.restaurants.v1.order.model.OrderModel;
import com.example.restaurants.v1.order.repository.OrderRepository;
import com.example.restaurants.v1.order.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;


  @Override
  public OrderModel createOrder(OrderModel orderModel) {
    OrderEntity orderEntity = new OrderEntity(orderModel);
    orderRepository.save(orderEntity);
    return new OrderModel(orderEntity);
  }

  @Override
  public OrderModel getById(String OrderId) {
    OrderEntity orderEntity = orderRepository.findById(new ObjectId(OrderId))
        .orElseThrow(() -> new RuntimeException("Order not found with ID: " + OrderId));
    return new OrderModel(orderEntity);
  }

  @Override
  public OrderModel markByOrderID(String OrderId) {
    OrderEntity orderEntity = orderRepository.findById(new ObjectId(OrderId))
        .orElseThrow(() -> new RuntimeException("Order not found with ID: " + OrderId));
    orderEntity.setStatus(OrderStatus.COMPLETED);
    orderRepository.save(orderEntity);
    return new OrderModel(orderEntity);
  }

  @Override
  public OrderModel cancelByOrderId(String OrderId) {
    OrderEntity orderEntity = orderRepository.findById(new ObjectId(OrderId))
        .orElseThrow(() -> new RuntimeException("Order not found with ID: " + OrderId));
    orderEntity.setStatus(OrderStatus.CANCELLED);
    orderRepository.save(orderEntity);
    return new OrderModel(orderEntity);
  }

  @Override
  public List<OrderModel> findByUserId(String userId) {
    List<OrderEntity> orders = orderRepository.findByUserId(userId);
    return orders.stream()
        .map(OrderModel::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderModel> findByRestaurantId(String restaurantId) {
    List<OrderEntity> orders = orderRepository.findByRestaurantId(new ObjectId(restaurantId));
    return orders.stream()
        .map(OrderModel::new)
        .collect(Collectors.toList());
  }
}
