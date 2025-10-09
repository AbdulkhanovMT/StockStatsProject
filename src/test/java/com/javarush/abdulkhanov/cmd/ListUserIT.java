package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class ListUserIT extends BaseIT {

    ListUser listUser = ServiceLocator.find(ListUser.class);

    @Test
    void doGet() {
        log.info("ListStore integration test");
        String uri = listUser.doGet(request);
        Assertions.assertEquals("list-user", uri);
    }
}