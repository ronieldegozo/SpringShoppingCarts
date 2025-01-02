package com.shoppingcart.shoppingcarts.repository;

import com.shoppingcart.shoppingcarts.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
