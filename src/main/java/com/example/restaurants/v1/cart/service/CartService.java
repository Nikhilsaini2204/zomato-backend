package com.example.restaurants.v1.cart.service;

import com.example.restaurants.v1.cart.entity.Cart;
import com.example.restaurants.v1.cart.entity.CartItem;
import com.example.restaurants.v1.cart.repository.CartItemRepository;
import com.example.restaurants.v1.cart.repository.CartRepository;
import com.example.restaurants.v1.dish.entity.DishEntity;
import com.example.restaurants.v1.dish.repository.DishRepository;
import com.example.restaurants.v1.inventory.service.InventoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

  @Autowired private CartRepository cartRepo;
  @Autowired private CartItemRepository itemRepo;
  @Autowired private DishRepository dishRepo;
  @Autowired private InventoryService inventoryService;

  public Cart getOrCreateCart(String userId) {
    return cartRepo.findByUserId(userId).orElseGet(() -> {
      Cart cart = new Cart();
      cart.setUserId(userId);
      return cartRepo.save(cart);
    });
  }

  public List<CartItem> getCartItems(String userId) {
    Cart cart = getOrCreateCart(userId);
    return itemRepo.findByCartId(cart.getId());
  }

  public void addItem(String userId, String dishId) {
    Cart cart = getOrCreateCart(userId);
    DishEntity dish = dishRepo.findById(new ObjectId(dishId))
        .orElseThrow(() -> new RuntimeException("Dish not found"));

    // Validate all existing items are from same restaurant throw error (500)
    List<CartItem> existingItems = itemRepo.findByCartId(cart.getId());
    if (!existingItems.isEmpty()) {
      String existingDishId = existingItems.get(0).getDishId();
      DishEntity existingDish = dishRepo.findById(new ObjectId(existingDishId))
          .orElseThrow(() -> new RuntimeException("Dish not found in cart"));

      String existingRestaurantId = String.valueOf(existingDish.getRestaurantId());
      String newDishRestaurantId = String.valueOf(dish.getRestaurantId());

      if (!existingRestaurantId.equals(newDishRestaurantId)) {
        throw new RuntimeException("Cannot add dishes from multiple restaurants to the cart.");
      }
    }

    // Check inventory
    CartItem item = itemRepo.findByCartIdAndDishId(cart.getId(), dishId)
        .orElseGet(() -> {
          CartItem newItem = new CartItem();
          newItem.setCartId(cart.getId());
          newItem.setDishId(dish.getId().toString());
          newItem.setDishName(dish.getName());
          newItem.setPrice(dish.getPrice());
          newItem.setQuantity(0);
          return newItem;
        });
    int currentQuantity = item.getQuantity();
    int requestedQuantity = currentQuantity + 1;
    int availableInventory = inventoryService.getAvailableQuantity(dishId);
    if (requestedQuantity > availableInventory) {
      throw new RuntimeException("Inventory do not have much quantity");
    }
    item.setQuantity(requestedQuantity);
    itemRepo.save(item);
    float totalAmount = 0.0f;
    List<CartItem> allItems = itemRepo.findByCartId(cart.getId());
    for (CartItem ci : allItems) {
      totalAmount += ci.getPrice() * ci.getQuantity();
    }
    cart.setAmount(totalAmount);
    cartRepo.save(cart);
  }

//  public void addItem(String userId, String dishId) {
//    Cart cart = getOrCreateCart(userId);
//
//    DishEntity dish = dishRepo.findById(new ObjectId(dishId))
//        .orElseThrow(() -> new RuntimeException("Dish not found"));
//
//    CartItem item = itemRepo.findByCartIdAndDishId(cart.getId(), dishId)
//        .orElseGet(() -> {
//          CartItem newItem = new CartItem();
//          newItem.setCartId(cart.getId());
//          newItem.setDishId(dish.getId().toString());
//          newItem.setDishName(dish.getName());
//          newItem.setPrice(dish.getPrice());
//          newItem.setQuantity(0);
//          return newItem;
//        });
//
//    item.setQuantity(item.getQuantity() + 1);
//    itemRepo.save(item);
//    List<CartItem> allItems = itemRepo.findByCartId(cart.getId());
//    float totalAmount = 0.0f;
//    for (CartItem ci : allItems) {
//      totalAmount += ci.getPrice() * ci.getQuantity();
//    }
//    cart.setAmount(totalAmount);
//    cartRepo.save(cart);
//  }

  public void decrementItem(String userId, String dishId) {
    Cart cart = getOrCreateCart(userId);
    Optional<CartItem> optionalItem = itemRepo.findByCartIdAndDishId(cart.getId(), dishId);

    optionalItem.ifPresent(item -> {
      if (item.getQuantity() <= 1) {
        itemRepo.delete(item);
      } else {
        item.setQuantity(item.getQuantity() - 1);
        itemRepo.save(item);
      }
    });
  }

  public void clearCart(String userId) {
    Cart cart = getOrCreateCart(userId);
    itemRepo.deleteAll(itemRepo.findByCartId(cart.getId()));
  }

  public float getCartAmount(String userId) {
    Cart cart = getOrCreateCart(userId);
    List<CartItem> items = itemRepo.findByCartId(cart.getId());

    float totalAmount = 0.0f;
    for (CartItem item : items) {
      totalAmount += item.getPrice() * item.getQuantity();
    }

    return totalAmount;
  }

}
