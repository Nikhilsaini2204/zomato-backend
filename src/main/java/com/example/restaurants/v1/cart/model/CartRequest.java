package com.example.restaurants.v1.cart.model;

import lombok.Data;

@Data
public class CartRequest {
  private String userId;
  private String dishId;
}
