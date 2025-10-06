package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SignupIT extends BaseIT {

    UserService userService = ServiceLocator.find(UserService.class);
    Signup signup = ServiceLocator.find(Signup.class);

    @Test
    void doPostWhenPasswordConfirmed() {
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn("testName");
        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn("testLogin");
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn("testPassword");
        Mockito.when(request.getParameter("repeatPass")).thenReturn("testPassword");
        Mockito.when(request.getParameter(AttributeKeys.ROLE)).thenReturn("GUEST");

        String uri = signup.doPost(request);
        Assertions.assertEquals(Address.PROFILE, uri);
        Assertions.assertTrue(userService.getAll().toString().contains("testLogin"));
    }

    @Test
    void doPostWhenPasswordIncorrect() {
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn("testName");
        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn("testLogin");
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn("testPassword");
        Mockito.when(request.getParameter("repeatPass")).thenReturn("wrongPassword");
        Mockito.when(request.getParameter(AttributeKeys.ROLE)).thenReturn("GUEST");

        String uri = signup.doPost(request);
        Assertions.assertEquals(Address.SIGNUP, uri);
        Assertions.assertFalse(userService.getAll().toString().contains("testLogin"));
    }
}