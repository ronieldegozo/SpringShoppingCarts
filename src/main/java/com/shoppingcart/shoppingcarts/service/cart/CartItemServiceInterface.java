package com.shoppingcart.shoppingcarts.service.cart;

public interface CartItemServiceInterface {
    void addItemToCart(Long id, Long productId, int quantity);
    void removeItemFromCart(Long id, Long productId);
    void updateItemQuantity(Long id, Long productId, int quantit, int quantity);
}
