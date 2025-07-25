package com.example.restaurants.v1.cart.repository;

import com.example.restaurants.v1.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByCartId(Long cartId);
  Optional<CartItem> findByCartIdAndDishId(Long cartId, String dishId);
}
