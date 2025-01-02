package com.shoppingcart.shoppingcarts.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int quantity;
    private BigDecimal price;

    @ManyToOne //Many order to one OrderItem
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne //Many Product Id to one orderItem
    @JoinColumn(name = "product_id")
    private Product product;


    public OrderItem(int quantity, BigDecimal price, Order order, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }

}