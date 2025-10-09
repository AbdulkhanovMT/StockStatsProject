package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class LoginIT extends BaseIT {

    Login login = ServiceLocator.find(Login.class);
    UserService userService = Mockito.mock(UserService.class);

    @Test
    void doPostWhenUserIsPresent() {
        log.info("Login IT doPostWhenUserIsPresent");
        String testLogin = "admin";
        String testPassword = "admin";

        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn(testLogin);
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn(testPassword);
        Mockito.when(request.getSession()).thenReturn(mockedSession);

        String uri = login.doPost(request);

        Assertions.assertEquals(Address.PROFILE, uri);
    }

    @Test
    void doPostWhenUserIsNotPresent() {
        log.info("Login IT doPostWhenUserIsNotPresent");
        String testLogin = "testLogin";
        String testPassword = "testPassword";

        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn(testLogin);
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn(testPassword);
        Mockito.when(request.getSession()).thenReturn(mockedSession);

        String uri = login.doPost(request);

        Assertions.assertEquals(Address.LOGIN, uri);
    }
}