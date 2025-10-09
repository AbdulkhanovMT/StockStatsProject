package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class ListStoreIT extends BaseIT {

    StoreService storeService = ServiceLocator.find(StoreService.class);
    ListStore listStore = ServiceLocator.find(ListStore.class);

    @Test
    void doGet() {
        log.info("ListStore integration test");
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testAdmin);

        String uri = listStore.doGet(request);
        Assertions.assertEquals("list-store", uri);
    }
}