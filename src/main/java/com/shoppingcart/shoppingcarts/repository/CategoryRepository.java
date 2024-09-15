package com.shoppingcart.shoppingcarts.repository;

import com.shoppingcart.shoppingcarts.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existByName(String name);
}
