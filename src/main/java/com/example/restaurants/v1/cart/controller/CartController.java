package com.example.restaurants.v1.cart.controller;

import com.example.restaurants.v1.cart.entity.CartItem;
import com.example.restaurants.v1.cart.model.CartRequest;
import com.example.restaurants.v1.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired private CartService cartService;

  @GetMapping("/{userId}")
  public List<CartItem> getCart(@PathVariable String userId) {
    return cartService.getCartItems(userId);
  }

  @PostMapping("/add")
  public void addDish(@RequestBody CartRequest req) {
    cartService.addItem(req.getUserId(), req.getDishId());
  }

  @PostMapping("/decrement")
  public void decrementDish(@RequestBody CartRequest req) {
    cartService.decrementItem(req.getUserId(), req.getDishId());
  }

  @DeleteMapping("/clear/{userId}")
  public void clear(@PathVariable String userId) {
    cartService.clearCart(userId);
  }

  @GetMapping("/amount")
  public ResponseEntity<Double> getCartAmount(@RequestParam String userId) {
    double amount = cartService.getCartAmount(userId);
    return ResponseEntity.ok(amount);
  }


}
