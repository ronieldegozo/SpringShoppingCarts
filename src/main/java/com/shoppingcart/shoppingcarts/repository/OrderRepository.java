package com.shoppingcart.shoppingcarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.shoppingcarts.model.Order;

public interface OrderRepository extends JpaRepository <Order, Long> {


}
