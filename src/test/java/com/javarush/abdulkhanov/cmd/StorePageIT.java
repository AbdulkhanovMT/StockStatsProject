package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

@Slf4j
class StorePageIT extends BaseIT {

    StoreService storeService = Mockito.mock(StoreService.class);
    StorePage storePage = ServiceLocator.find(StorePage.class);

    @Test
    void doGetWhenUserIsSellerOrAdmin() {
        log.info("StorePage IT doGetWhenUserIsSellerOrAdmin");
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testSeller);
        Mockito.when(request.getParameter("storeId")).thenReturn("1");

        String uri = storePage.doGet(request);
        Assertions.assertEquals(Address.STORE_PAGE, uri);
    }

    @Test
    void doGetWhenUserIsWrongSeller() {
        log.info("StorePage IT doGetWhenUserIsWrongSeller");
        User marketSpy = Mockito.mock(User.class);
        Mockito.when(marketSpy.getSellerApiKeyList()).thenReturn(List.of("wrongPass"));
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(marketSpy);
        Mockito.when(request.getParameter("storeId")).thenReturn("1");

        String uri = storePage.doGet(request);
        Assertions.assertEquals(Address.PROFILE, uri);
    }

    @Test
    void doGetWhenUserIsNotSellerOrAdmin() {
        log.info("StorePage IT doGetWhenUserIsNotSellerOrAdmin");
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testUser);
        Mockito.when(request.getParameter("storeId")).thenReturn("1");

        String uri = storePage.doGet(request);
        Assertions.assertEquals(Address.PROFILE, uri);
    }
}