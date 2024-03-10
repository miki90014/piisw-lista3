package com.example.orderservice.model;

import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private Double price;
    private int quantity;
}
