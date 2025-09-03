package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.Store;

import java.util.Objects;
import java.util.stream.Stream;

public class StoreRepository extends AbstractRepository<Store> {
    @Override
    public Stream<Store> find(Store entity) {
        return entities.values().stream()
                .filter(store -> store.getAccessKey().equals(entity.getAccessKey()))
                .filter(store -> Objects.equals(store.getStoreName(), entity.getStoreName()));
    }
}
