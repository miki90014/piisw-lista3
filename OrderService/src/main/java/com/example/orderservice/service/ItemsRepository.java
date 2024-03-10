package com.example.orderservice.service;

import com.example.orderservice.model.Items;
import com.example.orderservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    List<Items> findAllByOrdersId(Long orderId);
}
