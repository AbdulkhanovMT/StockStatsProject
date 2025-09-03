package com.javarush.abdulkhanov.service;

import com.javarush.abdulkhanov.entity.Order;
import com.javarush.abdulkhanov.repository.OrderRepository;

import java.util.Collection;
import java.util.Optional;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Collection<Order> getAll(){
        return orderRepository.getAll();
    }

    public Optional<Order> get(Long id){
        return Optional.ofNullable(orderRepository.get(id));
    }

    public void create(Order order){
        orderRepository.create(order);
    }

    public void update(Order order){
        orderRepository.update(order);
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }
}
