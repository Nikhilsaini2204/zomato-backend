package com.example.restaurants.v1.dish.controller;

import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.dish.model.DishModel;
import com.example.restaurants.v1.dish.service.DishService;
import com.example.restaurants.v1.restaurant.model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/dish")
public class DishController {
  @Autowired
  private DishService dishService;

  @PostMapping("/restaurant/{restaurantId}/{categoryId}/addItem")
  public ResponseEntity<DishModel> addDish(@RequestBody DishModel dish,
      @PathVariable String restaurantId, @PathVariable String categoryId) {
    DishModel dishModel = dishService.addDish(dish, restaurantId, categoryId);
    return new ResponseEntity<>(dishModel, HttpStatus.OK);
  }

  @PutMapping("/restaurant/{restaurantId}/updateItem/{dishId}")
  public ResponseEntity<DishModel> updateDish(@PathVariable String restaurantId,
      @PathVariable String dishId, @RequestBody DishModel dish) {
    DishModel dishModel = dishService.updateDish(dishId, dish);
    return new ResponseEntity<>(dishModel, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}/dishes/{dishId}")
  public ResponseEntity<DishModel> getDishById(@PathVariable String restaurantId,
      @PathVariable String dishId) {
    DishModel dishModel = dishService.getDishById(dishId);
    return new ResponseEntity<>(dishModel, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}/dishes")
  public ResponseEntity<List<DishModel>> getDishByRestaurantId(@PathVariable String restaurantId) {
    List<DishModel> dishModel = dishService.getDishByRestaurantId(restaurantId);
    return new ResponseEntity<>(dishModel, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}/dishes/category/{category}")
  public ResponseEntity<List<DishModel>> getDishByCategoryId(@PathVariable String category) {
    List<DishModel> dishModel = dishService.getDishByCategoryId(category);
    return new ResponseEntity<>(dishModel, HttpStatus.OK);
  }

  @DeleteMapping("/restaurant/{restaurantId}/{dishId}/deleteItem")
  public ResponseEntity<DishModel> deleteByDishId(@PathVariable String dishId) {
    DishModel dish = dishService.deleteByDishId(dishId);
    return new ResponseEntity<>(dish, HttpStatus.OK);
  }

  @GetMapping("/restaurant")
  public ResponseEntity<List<DishModel>> getDishesByRestaurantName(@RequestParam String name,
      @RequestParam(required = false) String dishName) {
    List<DishModel> dishes = dishService.getDishesByRestaurantName(name, dishName);
    return ResponseEntity.ok(dishes);
  }

  @GetMapping("/dishName")
  public ResponseEntity<List<DishModel>> getDishesByName(@RequestParam String name) {
    List<DishModel> dishes = dishService.getDishesByName(name);
    return ResponseEntity.ok(dishes);
  }

  @GetMapping("/restaurants/bycategory")
  public ResponseEntity<List<RestaurantModel>> getRestaurantsByCategory(
      @RequestParam String categoryName) {

    List<RestaurantModel> results = dishService.getRestaurantsByCategoryName(categoryName);
    return ResponseEntity.ok(results);
  }

  @GetMapping("/categories/by-restaurant")
  public ResponseEntity<List<CategoryModel>> getCategoriesByRestaurant(
      @RequestParam String restaurantName) {
    List<CategoryModel> categories = dishService.getCategoriesByRestaurantName(restaurantName);
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

}
