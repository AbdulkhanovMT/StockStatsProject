package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductPageIT extends BaseIT {

    private ProductPage productPage = ServiceLocator.find(ProductPage.class);
    private StoreService storeService = ServiceLocator.find(StoreService.class);

    @Test
    void doGetForSeller() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testSeller);
        Mockito.when(request.getParameter("productId")).thenReturn("2");
        Mockito.when(mockedSession.getAttribute("stores")).thenReturn(List.of(storeService.get(1L).get()));

        String uri = productPage.doGet(request);
        Assertions.assertEquals(Address.CARD, uri);
    }

    @Test
    void doGetForUserWithoutStores() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testSeller);
        Mockito.when(request.getParameter("productId")).thenReturn("2");
        Mockito.when(mockedSession.getAttribute("stores")).thenReturn(null);

        String uri = productPage.doGet(request);
        Assertions.assertEquals(Address.PROFILE, uri);
    }

    @Test
    void doGetWithLogout() {
        Mockito.when(request.getParameter(AttributeKeys.LOGOUT)).thenReturn(AttributeKeys.LOGOUT);

        String uri = productPage.doGet(request);
        Assertions.assertEquals(Address.LOGOUT, uri);
    }
}