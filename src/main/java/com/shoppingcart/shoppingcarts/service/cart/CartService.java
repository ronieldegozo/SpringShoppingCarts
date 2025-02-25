package com.shoppingcart.shoppingcarts.service.cart;

import com.shoppingcart.shoppingcarts.dto.CartDto;
import com.shoppingcart.shoppingcarts.dto.UserDto;
import com.shoppingcart.shoppingcarts.exceptions.CartNotFoundException;
import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.repository.CartItemRepository;
import com.shoppingcart.shoppingcarts.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements CartServiceInterface{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final ModelMapper modelMapper;


    @Override
    public Cart getCart(Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for ID: " + id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartItemRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);

        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUser(user);
                return cartRepository.save(cart);
            });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartDto convertUserToDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }
}
