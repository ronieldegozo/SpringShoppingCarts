package com.shoppingcart.shoppingcarts.service.category;

import com.shoppingcart.shoppingcarts.model.Category;

import java.util.List;

public interface InterfaceCategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
