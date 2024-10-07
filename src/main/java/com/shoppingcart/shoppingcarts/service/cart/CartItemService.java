package com.shoppingcart.shoppingcarts.service.cart;

import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.model.CartItems;
import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.repository.CartItemRepository;
import com.shoppingcart.shoppingcarts.repository.CartRepository;
import com.shoppingcart.shoppingcarts.service.product.InterfaceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceInterface{

    private final CartItemRepository cartItemRepository;
    private final InterfaceProductService productService;
    private final CartServiceInterface cartService;
    private final CartRepository cartRepository;


    @Override
    public void addItemToCart(Long id, Long productId, int quantity) {
        //1. Get cart
        //2. Get Product
        //3. Check if the product is already existing in cart
        //4. If exist, just increate the quantity of the items
        //5. If not, create a new cart item entry.

        //Get cart by id
        Cart cart = cartService.getCart(id);
        //Get Product by id
        Product product = productService.getProductById(id);

        CartItems cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItems());


        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long id, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long id, Long productId, int quantit, int quantity) {

    }
}
