package com.example.restaurants.v1.restaurant.controller;

import com.example.restaurants.v1.restaurant.model.RestaurantModel;
import com.example.restaurants.v1.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {
  @Autowired
  private RestaurantService restaurantService;

  @PostMapping("/create")
  public ResponseEntity<?> createRestaurant(@RequestBody RestaurantModel restaurantModel) {
    RestaurantModel savedRestaurant = restaurantService.createRestaurant(restaurantModel);
    if (savedRestaurant == null) {
      return ResponseEntity.ok().body("A restaurant already exists by this name");
    }
    return ResponseEntity.ok(savedRestaurant);
  }

  @GetMapping("/filters")
  public ResponseEntity<List<RestaurantModel>> getRestaurants(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String typeCuisine,
      @RequestParam(required = false) Boolean isAvailable) {
    List<RestaurantModel> restaurants =
        restaurantService.getRestaurantByFilter(name, typeCuisine, isAvailable);
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping("/name")
  public ResponseEntity<RestaurantModel> getRestaurantByName(@RequestParam String name) {
    RestaurantModel restaurantModel = restaurantService.getRestaurantByName(name);
    return ResponseEntity.ok(restaurantModel);
  }

  @GetMapping
  public ResponseEntity<RestaurantModel> getRestaurantBy(@RequestParam String restaurantId) {
    RestaurantModel restaurantModel = restaurantService.getRestaurantById(restaurantId);
    return ResponseEntity.ok(restaurantModel);
  }

  @GetMapping("/owner")
  public ResponseEntity<RestaurantModel> getRestaurantByOwner(@RequestParam String ownerId) {
    RestaurantModel restaurantModel = restaurantService.getRestaurantByOwner(ownerId);
    return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
  }

  @GetMapping("/{restaurantId}/dashboard")
  public ResponseEntity<String> showDashboard(@PathVariable String restaurantId) {
    String dashboard = restaurantService.getDashboard(restaurantId);
    return ResponseEntity.ok(dashboard);
  }

  @GetMapping("/{restaurantId}/analytics")
  public ResponseEntity<String> showAnalytics(@PathVariable String restaurantId) {
    String analytics = restaurantService.getAnalytics(restaurantId);
    return ResponseEntity.ok(analytics);
  }


  @PostMapping("/request/approve")
  public ResponseEntity<RestaurantModel> approveRequestStatusByName(@RequestParam String name) {
    RestaurantModel updated = restaurantService.approveRequestStatusByName(name);
    return ResponseEntity.ok(updated);
  }

  @PostMapping("/request/reject")
  public ResponseEntity<RestaurantModel> rejectRequestStatus(@RequestParam String name) {
    RestaurantModel updated = restaurantService.rejectRequestStatusByName(name);
    return ResponseEntity.ok(updated);
  }

  @GetMapping("/pending")
  public ResponseEntity<List<RestaurantModel>> getPendingRequests() {
    List<RestaurantModel> pendingRestaurants = restaurantService.getPendingRequests();
    return ResponseEntity.ok(pendingRestaurants);
  }
}
