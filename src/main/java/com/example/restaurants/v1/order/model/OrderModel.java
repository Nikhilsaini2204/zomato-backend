package com.example.restaurants.v1.order.model;

import com.example.restaurants.v1.order.entity.OrderEntity;
import com.example.restaurants.v1.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
  private String id;
  private String userId;
  private String restaurantId;
  private float amount;
  private Date placedOn;
  private OrderStatus status;
  private List<String> dishes;

  public OrderModel(OrderEntity orderEntity){
    this.id = orderEntity.getIdAsString();
    this.userId = orderEntity.getUserId();
    this.restaurantId = orderEntity.getRestaurantId().toString();
    this.amount = orderEntity.getAmount();
    this.placedOn = orderEntity.getPlacedOn();
    this.status = orderEntity.getStatus();
    this.dishes = orderEntity.getDishes()
        .stream()
        .map(ObjectId::toString)
        .collect(Collectors.toList());
  }
}
