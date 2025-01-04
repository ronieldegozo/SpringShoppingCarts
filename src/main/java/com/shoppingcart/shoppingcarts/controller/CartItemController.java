package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.exceptions.ProductNotFoundException;
import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.repository.UserRepository;
import com.shoppingcart.shoppingcarts.request.CartItemRequest;
import com.shoppingcart.shoppingcarts.request.UpdateCartItemRequest;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.cart.CartItemServiceInterface;
import com.shoppingcart.shoppingcarts.service.cart.CartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartitems")
public class CartItemController {

    public final CartItemServiceInterface cartItemService;
    public final CartServiceInterface cartService;
    public final UserRepository userRepository;

    /**
     * Add a new item to the cart
     * URL: POST /cartitems/{cartId}
     * @param cartId ID of the cart to which the item will be added
     * @param cartItemRequest Item details to add to the cart
     * @return ResponseEntity with success or failure message
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addCartItem (@RequestBody CartItemRequest cartItemRequest) {

        try {
            
            User user = userRepository.findById(1L).orElseThrow(() -> new ProductNotFoundException("User not found!"));
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), cartItemRequest.getProductId(), cartItemRequest.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Item added to cart successfully!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch (Exception e) {
            // Handling any other unforeseen errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to add item to cart", e.getMessage()));
        }
    }

    @DeleteMapping("/cartid/{cartId}/productid/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart (@PathVariable Long cartId, @PathVariable Long productId){
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Product remove from Cart!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestBody UpdateCartItemRequest updateCartItemRequest) {
        try {
            cartItemService.updateItemQuantity(updateCartItemRequest.getCartId(), updateCartItemRequest.getProductId(), updateCartItemRequest.getQuantity());
            return ResponseEntity.ok(new ApiResponse("Update Item Quantity Success!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
