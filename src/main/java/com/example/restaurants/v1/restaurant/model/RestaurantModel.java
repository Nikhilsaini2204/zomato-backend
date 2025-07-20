package com.example.restaurants.v1.restaurant.model;

import com.example.restaurants.v1.restaurant.entity.RestaurantEntity;
import com.example.restaurants.v1.restaurant.enums.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantModel {
    private String name;
    private String typeCuisine;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private String phoneNumber;
    private Boolean isAvailable;
    private List<String> address;
    private String restaurantImage;
    private Float rating;
    private String time;
    private Request request;
    public RestaurantModel(RestaurantEntity entity)
    {
        this.name = entity.getName();
        this.typeCuisine = entity.getTypeCuisine();
        this.openingHour = entity.getOpeningHour();
        this.closingHour = entity.getClosingHour();
        this.phoneNumber = entity.getPhoneNumber();
        this.address = entity.getAddress();
        this.restaurantImage = entity.getRestaurantImage();
        this.rating = entity.getRating();
        this.request = entity.getRequest();
        this.time = entity.getTime();
        this.isAvailable = entity.isAvailable();
    }

    public RestaurantModel(String name, String typeCuisine, LocalTime openingHour, LocalTime closingHour, String phoneNumber, List<String> address, String restaurantImage,
        boolean available) {
            this.name = name;
            this.address = address;
            this.typeCuisine = typeCuisine;
            this.isAvailable = available;
            this.openingHour = openingHour;
            this.closingHour = closingHour;
            this.phoneNumber = phoneNumber;
            this.restaurantImage = restaurantImage;
    }
}

