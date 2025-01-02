package com.shoppingcart.shoppingcarts.service.order;

import com.shoppingcart.shoppingcarts.model.Order;

public interface InterfaceOrderService {
    
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

}
