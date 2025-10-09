package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class ProductListIT extends BaseIT {

    ProductList productList = ServiceLocator.find(ProductList.class);

    @Test
    void doGetLive() {
        log.info("Login IT doGetLive");
        Mockito.when(request.getSession()).thenReturn(mockedSession);

        String uri = productList.doGet(request);
        Assertions.assertEquals(Address.LIST_OF_PRODUCTS, uri);
    }

    @Test
    void doGetLogout() {
        log.info("Login IT doGetLogout");
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(request.getParameter(AttributeKeys.LOGOUT)).thenReturn(AttributeKeys.LOGOUT);

        String uri = productList.doGet(request);
        Assertions.assertEquals(Address.LOGOUT, uri);
    }
}