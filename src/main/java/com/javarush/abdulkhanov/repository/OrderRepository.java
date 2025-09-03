package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.Order;

import java.util.stream.Stream;

public class OrderRepository extends AbstractRepository<Order>{
    @Override
    public Stream<Order> find(Order entity) {
        return entities.values().stream()
                .filter(order -> order.getId().equals(entity.getId()));
    }
}
