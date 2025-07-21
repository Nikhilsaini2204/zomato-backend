package com.example.restaurants.v1.inventory.repository;

import com.example.restaurants.v1.inventory.entity.InventoryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
  List<InventoryEntity> findByDishId(String dishId);

  void deleteByDishId(String dishId);
}
