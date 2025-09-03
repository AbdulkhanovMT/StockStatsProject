package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.ProductParameter;

import java.util.Objects;
import java.util.stream.Stream;

public class ProductParameterRepository extends AbstractRepository<ProductParameter> {

    @Override
    public Stream<ProductParameter> find(ProductParameter entity) {
        return entities.values().stream()
                .filter(product -> product.getName().equals(entity.getName()));
    }
}
