package com.example.restaurants.v1.inventory.service;

import com.example.restaurants.v1.inventory.entity.InventoryEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface InventoryService {
  InventoryEntity createInventory(String dishId, int quantity);
  int getAvailableQuantity(String dishId);
  void deleteByDishId(String dishId);
}

