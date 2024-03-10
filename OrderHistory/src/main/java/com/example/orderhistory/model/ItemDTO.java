package com.example.orderhistory.model;

import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private Double price;
    private int quantity;
}
