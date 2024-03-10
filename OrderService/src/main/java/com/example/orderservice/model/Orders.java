package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    @ManyToOne(fetch = LAZY)
    private Delivery delivery;
}
