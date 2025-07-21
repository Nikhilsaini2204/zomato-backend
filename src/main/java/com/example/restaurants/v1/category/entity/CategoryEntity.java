package com.example.restaurants.v1.category.entity;

import com.example.restaurants.v1.category.enums.Types;
import com.example.restaurants.v1.category.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Dish_Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

  @Id
  private ObjectId id;

  @Field("name")
  private String name;

  @Field("restaurantId")
  private ObjectId restaurantId;

  @Field("type")
  private Types type;

  @Field("categoryImg")
  private String categoryImg;

  public CategoryEntity(CategoryModel categoryModel) {
    this.categoryImg = categoryModel.getCategoryImg();
    this.type = categoryModel.getType();
    this.name = categoryModel.getName();
  }

}
