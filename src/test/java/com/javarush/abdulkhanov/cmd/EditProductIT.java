package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.utils.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EditProductIT extends BaseIT {

    private final ProductService productService = ServiceLocator.find(ProductService.class);
    private final EditProduct editProduct = ServiceLocator.find(EditProduct.class);

    @Test
    void doGetWhenProductIdIsCorrect() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(request.getParameter("productId")).thenReturn("1");

        String uri = editProduct.doGet(request);
        Assertions.assertEquals("edit-product", uri);
    }

    @Test
    void doGetWhenProductIsNotPresent() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(request.getParameter("productId")).thenReturn(Long.toString(Long.MAX_VALUE));

        Throwable exception = assertThrows(
                ApplicationException.class,
                () -> editProduct.doGet(request)
        );
        Assertions.assertEquals("Product was not found", exception.getMessage());
    }

    @Test
    void doPostWhenProductIdIsCorrect() {
        String productId = "1";
        String productAmount = Objects.requireNonNull(productService
                        .get(Long.parseLong(productId))
                        .orElse(null))
                .getTotalAmount()
                .toString();
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(request.getParameter("productId")).thenReturn(productId);
        Mockito.when(request.getParameter("totalAmount")).thenReturn(productAmount);

        String uri = editProduct.doPost(request);
        Assertions.assertEquals(Address.CARD + "?productId=" + productId, uri);

    }

    @Test
    void doPostWhenProductIsNotPresent() {
        Mockito.when(request.getParameter("productId")).thenReturn(Long.toString(Long.MAX_VALUE));
        Throwable exception = assertThrows(
                ApplicationException.class,
                () -> editProduct.doGet(request)
        );
        Assertions.assertEquals("Product was not found", exception.getMessage());
    }
}