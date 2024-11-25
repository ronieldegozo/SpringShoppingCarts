package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.exceptions.CartNotFoundException;
import com.shoppingcart.shoppingcarts.exceptions.ProductNotFoundException;
import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.cart.CartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {

    public final CartServiceInterface cartService;

    //Get Cart ById
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart (@PathVariable long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success!", cart));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Delete Cart By Id
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart (@PathVariable long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart Cleared!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/totalprice")
    public ResponseEntity<ApiResponse> getTotalAmount (@PathVariable long cartId){
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Total Price : ", totalPrice));
    }

}
