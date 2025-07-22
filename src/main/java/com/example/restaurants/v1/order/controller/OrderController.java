package com.example.restaurants.v1.order.controller;
import com.example.restaurants.v1.order.model.OrderModel;
import com.example.restaurants.v1.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/order")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping("/create")
  public ResponseEntity<OrderModel>createOrder(@RequestBody OrderModel orderModel){
    OrderModel model = orderService.createOrder(orderModel);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderModel>getOrderById(@PathVariable String orderId){
    OrderModel orderModel = orderService.getById(orderId);
    return new ResponseEntity<>(orderModel , HttpStatus.OK);
  }

  @PatchMapping("/{orderId}/completed")
  public ResponseEntity<OrderModel>markByOrderID(@PathVariable String orderId){
    OrderModel orderModel = orderService.markByOrderID(orderId);
    return new ResponseEntity<>(orderModel , HttpStatus.OK);
  }

  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<OrderModel>cancelByOrderId(@PathVariable String orderId){
    OrderModel orderModel = orderService.cancelByOrderId(orderId);
    return new ResponseEntity<>(orderModel , HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<OrderModel>> getOrdersByUserId(@RequestParam String userId) {
    List<OrderModel> orders = orderService.findByUserId(userId);
    return ResponseEntity.ok(orders);
  }
  @GetMapping("/restaurant")
  public ResponseEntity<List<OrderModel>> getOrdersByRestaurantId(@RequestParam String restaurantId) {
    List<OrderModel> orders = orderService.findByRestaurantId(restaurantId);
    return ResponseEntity.ok(orders);
  }
}
