package com.javarush.abdulkhanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store implements AbstractEntity{
    private Long id;
    private String storeName;
    private String accessKey;
    private List<User> userList = new ArrayList<>();
    private Collection<Order> orderList = new ArrayList<>();
    private Collection<Product> products = new ArrayList<>();

    public void setProducts(Collection<Product> products) {
        for (Product product : products) {
            product.setStoreId(this.getId());
        }
        this.products = products;
    }
}
