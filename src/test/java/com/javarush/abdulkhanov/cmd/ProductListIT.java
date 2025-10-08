package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductListIT extends BaseIT {

    ProductList productList = ServiceLocator.find(ProductList.class);

    @Test
    void doGetLive() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);

        String uri = productList.doGet(request);
        Assertions.assertEquals(Address.LIST_OF_PRODUCTS, uri);
    }

    @Test
    void doGetLogout() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(request.getParameter(AttributeKeys.LOGOUT)).thenReturn(AttributeKeys.LOGOUT);

        String uri = productList.doGet(request);
        Assertions.assertEquals(Address.LOGOUT, uri);
    }
}