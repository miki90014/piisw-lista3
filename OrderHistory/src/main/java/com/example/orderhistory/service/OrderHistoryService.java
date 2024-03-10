package com.example.orderhistory.service;

import com.example.orderhistory.model.ItemDTO;
import com.example.orderhistory.model.OrderHistory;
import com.example.orderhistory.model.OrdersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    public List<OrderHistory> getAll(){
        return orderHistoryRepository.findAll();
    }

    public OrderHistory getById(Long id){
        return orderHistoryRepository.getById(id);
    }

    public void addOrder(OrdersDTO ordersDTO, Long id) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrderId(id);
        orderHistory.setCustomerName(ordersDTO.getCustomerName());
        orderHistory.setCurierName(ordersDTO.getCourierName());
        orderHistory.setDeliveryStatus(ordersDTO.getDeliveryStatus().toString());
        Double totalPriceDouble = 0.0;
        String productsNames = "";
        for(ItemDTO itemDTO: ordersDTO.getItems()){
            totalPriceDouble += itemDTO.getQuantity() * itemDTO.getPrice();
            productsNames += itemDTO.getName() + " ";
        }
        BigDecimal totalPrice = new BigDecimal(totalPriceDouble);
        orderHistory.setTotalPrice(totalPrice);
        orderHistory.setProductNames(productsNames);
        orderHistoryRepository.save(orderHistory);
    }

    public void updateOrder(String delivery, Long id){
        OrderHistory orderHistory = orderHistoryRepository.getById(id);
        orderHistory.setDeliveryStatus(delivery);
        orderHistoryRepository.save(orderHistory);
    }


}
