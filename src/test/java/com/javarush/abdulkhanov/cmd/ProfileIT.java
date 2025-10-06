package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class ProfileIT extends BaseIT {

    Profile profile = ServiceLocator.find(Profile.class);
    StoreService storeService = ServiceLocator.find(StoreService.class);
    User user = Mockito.mock(User.class);

    @Test
    void doGetWhenUserIsAdmin() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testAdmin);
        Mockito.when(mockedSession.getAttribute("stores")).thenReturn((storeService.getAll()));

        Assertions.assertEquals(Address.PROFILE.substring(1), profile.doGet(request));
        Assertions.assertEquals(storeService.getAll(), mockedSession.getAttribute("stores"));
    }

    @Test
    void doGetWhenUserIsSeller() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testSeller);
        Mockito.when(mockedSession.getAttribute("stores")).thenReturn((storeService.get(1L).get()));
        Mockito.when(mockedSession.getAttribute("products")).thenReturn((storeService.get(1L).get().getProducts()));
        Mockito.when(user.getSellerApiKeyList()).thenReturn(List.of(storeService.get(1L).get().getAccessKey()));

        String uri = profile.doGet(request);
        Assertions.assertEquals(Address.PROFILE.substring(1), uri);
        Assertions.assertEquals(storeService.get(1L).get(), mockedSession.getAttribute("stores"));
        Assertions.assertEquals(storeService.get(1L).get().getProducts(), mockedSession.getAttribute("products"));
    }
}