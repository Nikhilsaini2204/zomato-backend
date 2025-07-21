package com.example.restaurants.v1.category.model;

import com.example.restaurants.v1.category.entity.CategoryEntity;
import com.example.restaurants.v1.category.enums.Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
  private String name;
  private Types type;
  private String id;
  private String categoryImg;

  public CategoryModel(CategoryEntity category) {
    this.type = category.getType();
    this.id = category.getId().toString();
    this.name = category.getName();
    this.categoryImg = category.getCategoryImg();
  }
}
