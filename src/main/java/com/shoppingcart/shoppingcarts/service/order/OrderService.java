package com.shoppingcart.shoppingcarts.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shoppingcart.shoppingcarts.enums.OrderStatus;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Cart;
import com.shoppingcart.shoppingcarts.model.CartItems;
import com.shoppingcart.shoppingcarts.model.Order;
import com.shoppingcart.shoppingcarts.model.OrderItem;
import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.repository.OrderRepository;
import com.shoppingcart.shoppingcarts.repository.ProductRepository;
import com.shoppingcart.shoppingcarts.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements InterfaceOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order Not Found!"));
    }

    private Order createOrder (Cart cart){
        Order order = new Order();

        order.setUser(cart.getUser());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getCartItems().stream().map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return  new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice());
                }).toList();
     }

    private BigDecimal calculateTotalAmount (List<OrderItem> orderItemList){
        return orderItemList.stream()
                            .map(items -> items.getPrice()
                            .multiply(new BigDecimal(items.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

  
    public List<Order> getUserOrders (Long userId) {
        return orderRepository.findByUserId(userId);
    }
}