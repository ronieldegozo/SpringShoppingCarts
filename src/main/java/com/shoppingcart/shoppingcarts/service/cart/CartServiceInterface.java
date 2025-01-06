package com.shoppingcart.shoppingcarts.service.cart;

import com.shoppingcart.shoppingcarts.dto.CartDto;
import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.model.User;

import java.math.BigDecimal;

public interface CartServiceInterface {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
    CartDto convertUserToDto(Cart cart);
}
