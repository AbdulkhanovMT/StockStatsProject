package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListUserIT extends BaseIT {

    ListUser listUser = ServiceLocator.find(ListUser.class);

    @Test
    void doGet() {
        String uri = listUser.doGet(request);
        Assertions.assertEquals("list-user", uri);
    }
}