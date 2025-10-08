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

        String uri = profile.doGet(request);
        Assertions.assertEquals(Address.PROFILE.substring(1), uri);
    }

    @Test
    void doGetWhenListOfUserStoreKeysIsNull() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testUser);

        String uri = profile.doGet(request);
        Assertions.assertEquals(Address.PROFILE.substring(1), uri);
    }

    @Test
    void doGetWhenLogout() {
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testUser);
        Mockito.when(request.getParameter("logout")).thenReturn("logout");

        String uri = profile.doGet(request);
        Assertions.assertEquals(Address.LOGOUT.substring(1), uri);
    }
}