package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.utils.Address;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LogoutIT extends BaseIT {

    private final Logout logout = ServiceLocator.find(Logout.class);

    @Test
    void doGet() {
        log.info("Login IT doGet");
        Mockito.when(request.getSession()).thenReturn(mockedSession);

        String uri = logout.doGet(request);
        Assertions.assertEquals(Address.LOGIN, uri);
    }
}