package com.shoppingcart.shoppingcarts.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long cartId;
    private Long productId;
    private int quantity;

}
