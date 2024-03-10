package com.example.orderservice.controller;
import com.example.orderservice.model.DeliveryStatus;
import com.example.orderservice.model.OrdersDTO;
import com.example.orderservice.service.OrderService;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order")
    @ResponseBody
    private List<OrdersDTO> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/order/{id}")
    @ResponseBody
    private OrdersDTO getOrder(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @PostMapping("/order")
    private void addOrder(@RequestBody OrdersDTO ordersDTO) {
        orderService.addOrder(ordersDTO);
    }

    @GetMapping("/order/{id}/delivered")
    private OrdersDTO delivered(@PathVariable("id") Long id) {
        return orderService.updateDelivery(id, DeliveryStatus.DELIVERED);
    }

    @GetMapping("/order/{id}/pickedUp")
    private OrdersDTO pickedUp(@PathVariable("id") Long id) {
        return orderService.updateDelivery(id, DeliveryStatus.PENDING);
    }
}
