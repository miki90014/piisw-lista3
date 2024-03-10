package com.example.orderservice.service;

import com.example.orderservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
