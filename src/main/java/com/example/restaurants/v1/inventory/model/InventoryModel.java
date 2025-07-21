package com.example.restaurants.v1.inventory.model;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryModel {
  private String dishId;
  private Integer quantity;
}
