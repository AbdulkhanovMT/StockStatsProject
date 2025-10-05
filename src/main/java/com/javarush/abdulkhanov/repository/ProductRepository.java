package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.Product;

import java.util.Objects;
import java.util.stream.Stream;

public class ProductRepository extends AbstractRepository<Product> {

    @Override
    public Stream<Product> find(Product entity) {
        return entities.values().stream()
                .filter(product -> Objects.equals(product.getName(), entity.getName()))
                .filter(product -> Objects.equals(product.getSku(), entity.getSku()));
    }
}
