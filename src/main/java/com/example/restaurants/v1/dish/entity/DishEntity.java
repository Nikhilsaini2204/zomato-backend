package com.example.restaurants.v1.dish.entity;


import com.example.restaurants.v1.dish.model.DishModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document(collection = "Dish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishEntity {

  @Id
  private ObjectId id;

  @Field("name")
  private String name;

  @Field("description")
  private String description;

  @Field("image")
  private String image;

  @Field("categoryId")
  private ObjectId categoryId;

  @Field("price")
  private Float price;

  @Field("prepTime")
  private int prepTime;

  @Field("restaurantId")
  private ObjectId restaurantId;

  @Field("dailyCapacity")
  private int dailyCapacity;

  @Field("isAvailable")
  private boolean isAvailable;

  @Field("review")
  private List<String> review;

  @Field("rating")
  private float rating;

  public String getIdAsString() {
    return id != null ? id.toHexString() : null;
  }

  public String getRestaurantIdAsString() {
    return restaurantId != null ? restaurantId.toHexString() : null;
  }

  public DishEntity(DishModel dishModel) {
    this.name = dishModel.getName();
    this.description = dishModel.getDescription();
    this.image = dishModel.getImage();
    this.price = dishModel.getPrice();
    this.prepTime = dishModel.getPrepTime();
    this.dailyCapacity = dishModel.getDailyCapacity();
    this.isAvailable = dishModel.isAvailable();
  }

}
