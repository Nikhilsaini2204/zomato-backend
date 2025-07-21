package com.example.restaurants.v1.order.model;

import com.example.restaurants.v1.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateEvent {
  private String orderId;
  private OrderStatus status;
}
