package com.example.restaurants.v1.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;

@Entity
@Table(name = "inventory_zomato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "dishId", nullable = false)
  private String dishId;

  @Column(name = "quantity", nullable = false)
  private int quantity;
}
