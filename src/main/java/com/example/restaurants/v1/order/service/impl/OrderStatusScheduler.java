package com.example.restaurants.v1.order.service.impl;

import com.example.restaurants.v1.order.entity.OrderEntity;
import com.example.restaurants.v1.order.enums.OrderStatus;
import com.example.restaurants.v1.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusScheduler {

  @Autowired
  private OrderRepository orderRepository;

  // Define the sequential status flow
  private static final List<OrderStatus> STATUS_FLOW = List.of(
      OrderStatus.PLACED,
      OrderStatus.CONFIRMED,
      OrderStatus.PREPARING,
      OrderStatus.OUTFORDELIVERY,
      OrderStatus.DELIVERED,
      OrderStatus.COMPLETED
  );

  @Scheduled(fixedRate = 60000) // every 1 minute
  public void autoUpdateOrderStatus() {
    List<OrderEntity> activeOrders = orderRepository.findByStatusNotIn(List.of(
        OrderStatus.CANCELLED,
        OrderStatus.COMPLETED
    ));

    for (OrderEntity order : activeOrders) {
      OrderStatus currentStatus = order.getStatus();
      int index = STATUS_FLOW.indexOf(currentStatus);

      // Only update if current status is in the flow and not the last one
      if (index >= 0 && index < STATUS_FLOW.size() - 1) {
        OrderStatus nextStatus = STATUS_FLOW.get(index + 1);
        order.setStatus(nextStatus);
        orderRepository.save(order);

        System.out.println("Order " + order.getId().toHexString() +
            " status updated from " + currentStatus + " to " + nextStatus);
      }
    }
  }
}
