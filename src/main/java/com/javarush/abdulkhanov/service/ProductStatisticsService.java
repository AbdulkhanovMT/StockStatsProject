package com.javarush.abdulkhanov.service;

import com.javarush.abdulkhanov.entity.Item;
import com.javarush.abdulkhanov.entity.ItemState;
import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.ProductStatistics;
import com.javarush.abdulkhanov.repository.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public class ProductStatisticsService {
    private ProductRepository productRepository;

    public ProductStatistics getTotalStatistics(){
        long sold = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getSold)
                .toList());
        long stock = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getStock)
                .toList());
        long cancelled = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getCancelled)
                .toList());
        long returned = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getReturned)
                .toList());
        long ordered = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getOrdered)
                .toList());
        long delivered = this.getStateSum(this.getProductStatisticsStream()
                .map(ProductStatistics::getDelivered)
                .toList());
        return ProductStatistics.builder()
                .sold(sold)
                .stock(stock)
                .cancelled(cancelled)
                .ordered(ordered)
                .delivered(delivered)
                .returned(returned)
                .build();
    }

    public Optional<ProductStatistics> getExactProductStatistics(Product product){
        return getEveryProductStatistics().stream()
                .filter(p -> p.getProductId().equals(product.getId()))
                .findFirst();
    }

    private long getStateSum(List<Long> list){
        return list.stream().mapToLong(Long::longValue).sum();
    }

    private Collection<ProductStatistics> getEveryProductStatistics() {
        return getProductStatisticsStream()
                .toList();
    }

    private Stream<ProductStatistics> getProductStatisticsStream() {
        return productRepository.getAll()
                .stream()
                .map(this::getProductStatistics);
    }

    private ProductStatistics getProductStatistics(Product product) {
        List<Item> itemList = (List<Item>) productRepository.find(product).findAny().get().getItems();
        Stream<Item> itemStream = itemList.stream();
        long stock = itemStream.filter(item -> item.getState() == ItemState.STOCK).count();
        long sold = itemStream.filter(item -> item.getState() == ItemState.SOLD).count();
        long cancelled = itemStream.filter(item -> item.getState() == ItemState.CANCELLED).count();
        long returned = itemStream.filter(item -> item.getState() == ItemState.RETURNED).count();
        long ordered = itemStream.filter(item -> item.getState() == ItemState.ORDERED).count();
        long delivered = itemStream.filter(item -> item.getState() == ItemState.DELIVERED).count();
        return ProductStatistics.builder()
                .stock(stock)
                .sold(sold)
                .cancelled(cancelled)
                .returned(returned)
                .ordered(ordered)
                .delivered(delivered)
                .build();
    }

}
