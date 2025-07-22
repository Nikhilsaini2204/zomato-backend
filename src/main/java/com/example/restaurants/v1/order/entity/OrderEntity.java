package com.example.restaurants.v1.order.entity;
import com.example.restaurants.v1.order.enums.OrderStatus;
import com.example.restaurants.v1.order.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Order_Zomato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
  @Id
  @Field("id")
  private ObjectId id;

  @Field("userId")
  private String userId;

  @Field("restaurantId")
  private ObjectId restaurantId;

  @Field("amount")
  private float amount;

  @Field("placedOn")
  private Date placedOn;

  @Field("status")
  private OrderStatus status;

  @Field("dishes")
  private List<ObjectId> dishes;

  public String getIdAsString() {
    return id != null ? id.toHexString() : null;
  }

  public OrderEntity(OrderModel orderModel){
    this.userId = orderModel.getUserId();
    this.restaurantId = new ObjectId(orderModel.getRestaurantId());
    this.amount = orderModel.getAmount();
    this.placedOn = new Date();
    this.status = OrderStatus.PLACED;
    this.dishes = orderModel.getDishes()
        .stream()
        .map(ObjectId::new)
        .collect(Collectors.toList());
  }


}
