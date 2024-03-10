package com.example.orderservice.service;

import com.example.orderservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final ItemsRepository itemsRepository;
    private final RestTemplate restTemplate;

    public List<OrdersDTO> getAll() {
        List<OrdersDTO> orders = new ArrayList<>();
        for (Orders order : orderRepository.findAll()) {
            OrdersDTO newOrdersDTO = new OrdersDTO();
            List<ItemDTO> itemDTOList = new ArrayList<>();
            newOrdersDTO.setCustomerName(order.getCustomerName());
            newOrdersDTO.setCourierName(order.getDelivery().getCourierName());
            newOrdersDTO.setDeliveryStatus(order.getDelivery().getDeliveryStatus());
            for(Items items : itemsRepository.findAllByOrdersId(order.getId())){
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setName(items.getProduct().getName());
                itemDTO.setQuantity(items.getQuantity());
                itemDTO.setPrice(items.getProduct().getPrice());
                itemDTO.setQuantity(items.getQuantity());
                itemDTOList.add(itemDTO);
            }
            newOrdersDTO.setItems(itemDTOList);
            orders.add(newOrdersDTO);
        }
        return orders;
    }

    public void addOrder(OrdersDTO ordersDTO) {
        Orders order = new Orders();
        order.setCustomerName(ordersDTO.getCustomerName());

        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(ordersDTO.getDeliveryStatus());
        delivery.setCourierName(ordersDTO.getCourierName());
        deliveryRepository.save(delivery);
        order.setDelivery(delivery);
        Long id = orderRepository.save(order).getId();

        ArrayList<Items> items = new ArrayList<>();
        for (ItemDTO itemDTO : ordersDTO.getItems()) {
            Product product = new Product();
            product.setName(itemDTO.getName());
            product.setPrice(itemDTO.getPrice());
            productRepository.save(product);


            Items ordersItem = new Items();
            ordersItem.setQuantity(itemDTO.getQuantity());
            ordersItem.setProduct(product);
            ordersItem.setOrders(order);
            itemsRepository.save(ordersItem);
            items.add(ordersItem);
        }
        String orderHistoryUrl = "http://localhost:8081/orderHistory/" + id;
        System.out.println(orderHistoryUrl);
        ResponseEntity<String> response = restTemplate.postForEntity(orderHistoryUrl, ordersDTO, String.class);
        System.out.println("Response status code: " + response.getStatusCodeValue());
    }

    public OrdersDTO getById(Long id) {
        Optional<Orders> order  = orderRepository.findById(id);
        if(order.isEmpty()){
            return null;
        }
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setCustomerName(order.get().getCustomerName());
        ordersDTO.setDeliveryStatus(order.get().getDelivery().getDeliveryStatus());
        ordersDTO.setCourierName(order.get().getDelivery().getCourierName());

        ArrayList<ItemDTO> itemDTOList = new ArrayList<>();
        for(Items items : itemsRepository.findAllByOrdersId(order.get().getId())){
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName(items.getProduct().getName());
            itemDTO.setQuantity(items.getQuantity());
            itemDTO.setPrice(items.getProduct().getPrice());
            itemDTO.setQuantity(items.getQuantity());
            itemDTOList.add(itemDTO);
        }
        ordersDTO.setItems(itemDTOList);
        return ordersDTO;
    }

    public OrdersDTO updateDelivery(Long id, DeliveryStatus status) {
        Optional<Orders> order  = orderRepository.findById(id);
        if(order.isEmpty()){
            return null;
        }
        Delivery delivery = order.get().getDelivery();
        delivery.setDeliveryStatus(status);
        deliveryRepository.save(delivery);
        String orderHistoryUrl = "http://localhost:8081/orderHistory/" + status.toString() + "/" + id;
        System.out.println(orderHistoryUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(orderHistoryUrl, String.class);
        System.out.println("Response status code: " + response.getStatusCodeValue());
        return getById(id);
    }

}
