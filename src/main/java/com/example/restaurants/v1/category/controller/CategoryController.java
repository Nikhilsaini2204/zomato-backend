package com.example.restaurants.v1.category.controller;


import com.example.restaurants.v1.category.model.CategoryModel;
import com.example.restaurants.v1.category.service.CategoryService;
import com.example.restaurants.v1.dish.model.DishModel;
import com.example.restaurants.v1.dish.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/category")
public class CategoryController {

@Autowired
  private CategoryService categoryService;

@Autowired
private DishService dishService;

@PostMapping("/{restaurantId}/create")
  public ResponseEntity<CategoryModel> createCategory(@PathVariable String restaurantId , @RequestBody CategoryModel categoryModel){
    CategoryModel model = categoryService.createCategory(restaurantId , categoryModel);
    return new ResponseEntity<>(model , HttpStatus.OK);
  }

  @PostMapping("/standard")
  public ResponseEntity<CategoryModel> createStandardCategory(@RequestBody CategoryModel categoryModel) {
    CategoryModel created = categoryService.createCategorySTD(categoryModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryModel> getByCategory (@PathVariable String categoryId){
   CategoryModel categoryModel = categoryService.getById(categoryId);
    return new ResponseEntity<>(categoryModel,HttpStatus.OK);
  }

  @GetMapping("/dishes")
  public ResponseEntity<List<DishModel>> getByCategoryName(@RequestParam String categoryName)
  {
    List<DishModel> ls = dishService.getDishesByCategoryName(categoryName.toLowerCase());
    return ResponseEntity.ok(ls);
  }
  @GetMapping
  public  ResponseEntity<List<CategoryModel>> getStandardCategory(){
    List<CategoryModel> ls = categoryService.getStandardCategory();
    return new ResponseEntity<>(ls,HttpStatus.OK);
  }

  @GetMapping("/type/{restaurantId}/CUSTOM")
  public  ResponseEntity<List<CategoryModel>> getByTypeAndRestaurantID(@PathVariable String restaurantId){
    List<CategoryModel> ls = categoryService.getCategoryByTypeAndRestaurantID(restaurantId );
    return new ResponseEntity<>(ls,HttpStatus.OK);
  }

  @GetMapping("/type/restaurantName")
  public  ResponseEntity<List<CategoryModel>> getByRestaurantName(@RequestParam String restaurantName){
    List<CategoryModel> ls = categoryService.getByRestaurantName(restaurantName);
    return new ResponseEntity<>(ls,HttpStatus.OK);
  }

  @GetMapping("/dishes/restCat")
  public ResponseEntity<List<DishModel>> getDishesByRestaurantAndCategory(
      @RequestParam String restaurantName,
      @RequestParam String categoryName
  )
  {
    List<DishModel> dishes = categoryService.getDishesByRestaurantAndCategory(restaurantName, categoryName);
    return ResponseEntity.ok(dishes);
  }

}
