package com.example.orderservice.service;

import com.example.orderservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findById(Long id);
}
