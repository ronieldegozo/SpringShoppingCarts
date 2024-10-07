package com.shoppingcart.shoppingcarts.repository;

import com.shoppingcart.shoppingcarts.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<Cart, Long> {
    void deleteAllByCartId(Long id);
}
