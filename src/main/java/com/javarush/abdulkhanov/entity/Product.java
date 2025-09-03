package com.javarush.abdulkhanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements AbstractEntity {
    private Long id;
    private Long storeId;
    private String name;
    private String sku;
    private String category;
    private Long totalAmount;
    private Collection<ProductParameter> parameters = new ArrayList<>();
    private Collection<Item>  items = new ArrayList<>();
}
