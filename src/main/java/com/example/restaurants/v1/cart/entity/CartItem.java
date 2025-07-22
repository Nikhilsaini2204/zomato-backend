package com.example.restaurants.v1.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "cartId")
  private Long cartId;

  @Column(name = "dishId")
  private String dishId;

  @Column(name = "dishName")
  private String dishName;

  @Column(name = "price")
  private Float price;

  @Column(name = "quantity")
  private Integer quantity;



}
