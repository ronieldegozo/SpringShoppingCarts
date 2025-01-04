package com.shoppingcart.shoppingcarts.service.order;

import java.util.List;

import com.shoppingcart.shoppingcarts.dto.OrderDto;
import com.shoppingcart.shoppingcarts.model.Order;

public interface InterfaceOrderService {
    
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
