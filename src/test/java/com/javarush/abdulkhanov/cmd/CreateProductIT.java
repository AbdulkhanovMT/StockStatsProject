package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import com.javarush.abdulkhanov.utils.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
class CreateProductIT extends BaseIT {

    private final ProductService productService = ServiceLocator.find(ProductService.class);
    private final CreateProduct createProduct = ServiceLocator.find(CreateProduct.class);

    @Test
    void doPost() {
        log.info("CreateProduct integration test");
        String storeId = "1";
        String name = "Fedor";
        String sku = "storeSku";
        String category = ProductCategory.SUITCASE;
        String totalAmount = Long.toString(ThreadLocalRandom.current().nextLong(0, 1000));
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn(name);
        Mockito.when(request.getParameter("sku")).thenReturn(sku);
        Mockito.when(request.getParameter("category")).thenReturn(category);
        Mockito.when(request.getParameter("totalAmount")).thenReturn(totalAmount);

        int count = productService.getAll().size();
        String uri = createProduct.doPost(request);
        Long createdProductId = productService.getAll().stream()
                .min((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .get().getId();
        Assertions.assertEquals(count + 1, productService.getAll().size());
        String expectedUri = Address.FILL_PRODUCT_PARAMETERS + "?category=" + category + "&storeId=" + storeId + "&productId=" + createdProductId;
        Assertions.assertEquals(expectedUri, uri);
    }

    @Test
    void doPostWhenProductNotFound() {
        log.info("CreateProduct integration test");
        String storeId = "1";
        String name = "";
        String sku = "";
        String category = "";
        String totalAmount = "";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn(name);
        Mockito.when(request.getParameter("sku")).thenReturn(sku);
        Mockito.when(request.getParameter("category")).thenReturn(category);
        Mockito.when(request.getParameter("totalAmount")).thenReturn(totalAmount);

        Throwable exception = Assertions.assertThrows(
                ApplicationException.class,
                () -> {
                    createProduct.doPost(request);
                });
        Assertions.assertEquals("Product not found", exception.getMessage());
    }
}