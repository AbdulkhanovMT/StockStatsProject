package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StartIT extends BaseIT {

    private final Start start = ServiceLocator.find(Start.class);

    @Test
    void doGetWhenUserIsGuest() {
        log.info("Start IT doGetWhenUserIsGuest");
        Mockito.when(request.getSession(true)).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testGuest);

        String uri = start.doGet(request);
        Assertions.assertEquals("start", uri);
    }

    @Test
    void doGetWhenUserIsAuthorized() {
        log.info("Start IT doGetWhenUserIsAuthorized");
        Mockito.when(request.getSession(true)).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testUser);

        String uri = start.doGet(request);
        Assertions.assertEquals(Address.PROFILE.substring(1), uri);
    }
}