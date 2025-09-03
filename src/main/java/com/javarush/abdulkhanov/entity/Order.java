package com.javarush.abdulkhanov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements AbstractEntity{
    private Long id;
    private Long storeId;
    private LocalDateTime orderDate;
    private Collection<Product> products;
}
