package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.exceptions.InvalidRequest;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Category;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.category.InterfaceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final InterfaceCategoryService categoryService;

    private static final String CATEGORY_NAME_REGEX = "^[a-zA-Z\\s]+$"; // Only alphabetic characters and spaces allowed


    @GetMapping
    public ResponseEntity<ApiResponse> getAllServices(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return  ResponseEntity.ok(new ApiResponse("Categories Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Can't find Categories", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // only admin can add the category
    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            String categoryName = name.getName();

            if (!categoryName.matches(CATEGORY_NAME_REGEX) || categoryName.length() < 4) {
                throw new InvalidRequest("Invalid category payload: " + categoryName);
            }

            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Category added successfully!", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));

        }
    }

    @GetMapping("/id/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById (@PathVariable Long categoryId){
        try {
            Category theCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Id Found!", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

   @GetMapping("/name/{categoryName}")
   public ResponseEntity<ApiResponse> getCategoryByName (@PathVariable String categoryName){
       try {
           Category theCategory = categoryService.getCategoryByName(categoryName);
           return ResponseEntity.ok(new ApiResponse("Category Name Found!", theCategory));
       } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse("Can't find Categories Name", e.getMessage()));
       }
   }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // only admin can delete the category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory (@PathVariable Long categoryId){
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // only admin can update the category
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory (@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            Category theCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Name Found!", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }



}
