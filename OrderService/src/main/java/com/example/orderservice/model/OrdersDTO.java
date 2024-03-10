package com.example.orderservice.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDTO {
    private String customerName;
    private List<ItemDTO> items;
    private String courierName;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
