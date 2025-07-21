package com.example.restaurants.v1.dish.model;

import com.example.restaurants.v1.dish.entity.DishEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishModel {
  private String name;
  private String description;
  private String image;
  private Float price;
  private boolean isAvailable;
  private int dailyCapacity;
  private int prepTime;

  public DishModel(DishEntity dishEntity) {
    this.name = dishEntity.getName();
    this.description = dishEntity.getDescription();
    this.image = dishEntity.getImage();
    this.prepTime = dishEntity.getPrepTime();
    this.price = dishEntity.getPrice();
    this.isAvailable = dishEntity.isAvailable();
    this.dailyCapacity = dishEntity.getDailyCapacity();
  }
}
