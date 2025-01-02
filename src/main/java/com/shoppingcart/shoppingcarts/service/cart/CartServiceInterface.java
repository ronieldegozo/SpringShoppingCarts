package com.shoppingcart.shoppingcarts.service.cart;

import com.shoppingcart.shoppingcarts.model.Cart;

import java.math.BigDecimal;

public interface CartServiceInterface {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}
