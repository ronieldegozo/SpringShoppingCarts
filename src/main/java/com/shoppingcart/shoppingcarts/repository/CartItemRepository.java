package com.shoppingcart.shoppingcarts.repository;

import com.shoppingcart.shoppingcarts.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    void deleteAllByCartId(Long id);
}
