package com.example.orderhistory.controller;

import com.example.orderhistory.model.OrderHistory;
import lombok.RequiredArgsConstructor;
import com.example.orderhistory.model.OrdersDTO;
import org.springframework.web.bind.annotation.*;
import com.example.orderhistory.service.OrderHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;

    @GetMapping("/orderHistory")
    @ResponseBody
    private List<OrderHistory> getAllOrders() {
        return orderHistoryService.getAll();
    }

    @GetMapping("/orderHistory/{id}")
    @ResponseBody
    private OrderHistory getOrderHistory(@PathVariable("id") Long id) {
        return orderHistoryService.getById(id);
    }

    @PostMapping("/orderHistory/{id}")
    private void addOrder(@RequestBody OrdersDTO ordersDTO, @PathVariable("id") Long id) {
        orderHistoryService.addOrder(ordersDTO, id);
    }

    @GetMapping("/orderHistory/{delivery}/{id}")
    private void updateDelivery(@PathVariable("delivery") String delivery, @PathVariable("id") Long id) {
        orderHistoryService.updateOrder(delivery, id);
    }


}
