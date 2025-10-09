package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.service.ProductParameterService;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FillProductParameterIT extends BaseIT {

    private final ProductParameterService productParameterService = ServiceLocator.find(ProductParameterService.class);
    private final ProductService productService = ServiceLocator.find(ProductService.class);
    private final FillProductParameter fillProductParameter = ServiceLocator.find(FillProductParameter.class);

    @Test
    void doGet() {
        log.info("GetProduct integration test");
        String storeId = "1";
        String category = ProductCategory.SUITCASE;
        String productId = "1";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("category")).thenReturn(category);
        Mockito.when(request.getParameter("productId")).thenReturn(productId);

        String uri = fillProductParameter.doGet(request);
        Assertions.assertEquals("fill-product-parameter", uri);
    }

    @Test
    void doGetWhenProductIdIsNull() {
        log.info("GetProduct integration test");
        String storeId = "1";
        String category = ProductCategory.SUITCASE;
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("category")).thenReturn(category);
        Mockito.when(request.getParameter("productId")).thenReturn(null);

        String uri = fillProductParameter.doGet(request);
        Assertions.assertEquals("fill-product-parameter", uri);
    }

    @Test
    void doPostWhenProductWasNotCreated() {
        log.info("CreateProduct integration test");
        String storeId = "1";
        String productId = Long.toString(productService.getAll().stream().max(Comparator.comparing(Product::getId)).get().getId()+1L);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("productId")).thenReturn(productId);

        String uri = fillProductParameter.doPost(request);
        Assertions.assertEquals(Address.CREATE_PRODUCT, uri);
    }

    @Test
    void doPost() {
        log.info("CreateProduct integration test");
        String storeId = "1";
        String productId = "1";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("productId")).thenReturn(productId);

        String uri = fillProductParameter.doPost(request);
        String expected = Address.CARD + "?productId=" + productId;
        Assertions.assertEquals(expected, uri);
    }
}