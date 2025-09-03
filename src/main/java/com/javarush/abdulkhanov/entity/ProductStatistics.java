package com.javarush.abdulkhanov.entity;

import lombok.*;

@Data
@Builder
public class ProductStatistics {
    private Long productId;
    private long stock;
    private long sold;
    private long cancelled;
    private long returned;
    private long ordered;
    private long delivered;
}
