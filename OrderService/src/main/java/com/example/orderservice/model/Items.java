package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@ToString
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    private Product product;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="orders_id")
    private Orders orders;
}
