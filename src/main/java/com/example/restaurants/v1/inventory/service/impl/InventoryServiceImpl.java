package com.example.restaurants.v1.inventory.service.impl;

import com.example.restaurants.v1.inventory.entity.InventoryEntity;
import com.example.restaurants.v1.inventory.repository.InventoryRepository;
import com.example.restaurants.v1.inventory.service.InventoryService;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Override
  public InventoryEntity createInventory(String dishId, int quantity) {
    if (dishId == null || !ObjectId.isValid(dishId)) {
      throw new IllegalArgumentException("Invalid dishId format: " + dishId);
    }
    InventoryEntity inventory = new InventoryEntity(null, dishId, quantity);
    return inventoryRepository.save(inventory);
  }

  @Override
  @Transactional
  public void deleteByDishId(String dishId) {
    inventoryRepository.deleteByDishId(dishId);
  }

  public int getAvailableQuantity(String dishId) {
    InventoryEntity inventory = inventoryRepository.findByDishId(dishId);
    if (inventory == null) {
      throw new RuntimeException("Inventory not found for dish ID: " + dishId);
    }
    return inventory.getQuantity();
  }
}
