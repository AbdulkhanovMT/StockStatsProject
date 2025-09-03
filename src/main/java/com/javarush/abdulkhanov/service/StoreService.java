package com.javarush.abdulkhanov.service;

import com.javarush.abdulkhanov.entity.Order;
import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.repository.OrderRepository;
import com.javarush.abdulkhanov.repository.ProductRepository;
import com.javarush.abdulkhanov.repository.StoreRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class StoreService {
    private StoreRepository storeRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public Collection<Store> getAll() {
        return storeRepository.getAll();
    }

    public Optional<Store> get(Long id) {
        return Optional.ofNullable(storeRepository.get(id));
    }

    public Optional<Store> get(String storeName, String accessKey) {
        Store possibleStore = Store.builder()
                .storeName(storeName)
                .accessKey(accessKey)
                .build();
        Optional<Store> optionalOfStore = storeRepository.find(possibleStore).findAny();
        if (optionalOfStore.isPresent()) {
            return optionalOfStore;
        } else {
            throw new ApplicationException("Store not found or access key is not correct");
        }
    }

    public void create(String name, String accessKey, List<Product> products, List<Order> orders) {
        Store store = Store.builder()
                .storeName(name)
                .accessKey(accessKey)
                .build();
        addOrdersToStore(store);
        putProductsToStore(store);
        for (Product product : products) {
            if (product.getStoreId().equals(store.getId()))
                productRepository.create(product);
        }
        for (Order order : orders) {
            if (order.getStoreId().equals(store.getId()))
                orderRepository.create(order);
        }
        storeRepository.create(store);
    }

    private void addOrdersToStore(Store store) {
        List<Order> orders = orderRepository.getAll()
                .stream()
                .filter(product -> Objects.equals(product.getStoreId(), store.getId()))
                .toList();
        store.setOrderList(orders);

    }

    private void putProductsToStore(Store store) {
        List<Product> storeProducts = productRepository.getAll()
                .stream()
                .filter(product -> Objects.equals(product.getStoreId(), store.getId()))
                .toList();
        store.setProducts(storeProducts);
    }

    public void create(Store store) {
        Store pattern = Store.builder()
                .storeName(store.getStoreName())
                .build();
        if (storeRepository.find(pattern).findAny().isPresent()) {
            throw new ApplicationException("Store already exists");
        } else {
            storeRepository.create(store);
        }
    }

    public void update(Store store) {
        storeRepository.update(store);
    }

    public void delete(Store store) {
        storeRepository.delete(store);
    }
}
