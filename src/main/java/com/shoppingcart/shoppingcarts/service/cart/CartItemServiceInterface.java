package com.shoppingcart.shoppingcarts.service.cart;

import com.shoppingcart.shoppingcarts.model.CartItems;

public interface CartItemServiceInterface {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItems getCartItem(Long cartId, Long productId);
}
