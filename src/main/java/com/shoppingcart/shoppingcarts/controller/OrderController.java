package com.shoppingcart.shoppingcarts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Order;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.order.InterfaceOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final InterfaceOrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCartOrder (@RequestParam Long userId){
        
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order Created", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error occured!", e.getMessage()));
        }

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById (@PathVariable Long orderId){
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order Retrieve!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order Not Found!", e.getMessage()));
        }

    }


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById (@PathVariable Long userId){
        try {
            List<Order> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order Retrieve!", userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order Not Found!", e.getMessage()));
        }

    }
}
