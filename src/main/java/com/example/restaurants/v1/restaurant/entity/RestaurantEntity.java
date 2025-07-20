package com.example.restaurants.v1.restaurant.entity;

import com.example.restaurants.v1.restaurant.enums.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.List;

@Document(collection = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantEntity {

  @Id
  @Field("id")
  private ObjectId id;

  @Field("name")
  private String name;

  @Field("typeCuisine")
  private String typeCuisine;

  @Field("openingHour")
  private LocalTime openingHour;

  @Field("closingHour")
  private LocalTime closingHour;

  @Field("phoneNumber")
  private String phoneNumber;

  @Field("address")
  private List<String> address;

  @Field("rating")
  private Float rating;

  @Field("reviews")
  private List<String> reviews;

  @Field("inventoryId")
  private Long inventoryId;

  @Field("isAvailable")
  private boolean isAvailable;

  @Field("restaurantImage")
  private String restaurantImage;

  @Field("time")
  private String time;

  @Field("request")
  private Request request;
}
