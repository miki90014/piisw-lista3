package com.example.orderhistory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@ToString
public class OrderHistory {
    @Id
    private Long orderId;
    private String customerName;
    private String curierName;
    private String deliveryStatus;
    private String productNames;
    private BigDecimal totalPrice;
}
