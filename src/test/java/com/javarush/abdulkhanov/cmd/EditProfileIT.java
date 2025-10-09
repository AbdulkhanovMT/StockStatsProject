package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.BaseIT;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class EditProfileIT extends BaseIT {

    private final EditProfile editProfile = ServiceLocator.find(EditProfile.class);
    private final UserService userService = ServiceLocator.find(UserService.class);

    @Test
    void doGet() {
        log.info("GetProduct integration test");
        Mockito.when(request.getParameter(AttributeKeys.ID)).thenReturn(testAdmin.getId().toString());

        String uri = editProfile.doGet(request);
        Assertions.assertEquals("edit-profile", uri);
    }

    @Test
    void doGetWhenIdIsNull() {
        log.info("GetProduct integration test");
        Mockito.when(request.getParameter(AttributeKeys.ID)).thenReturn(null);

        String uri = editProfile.doGet(request);
        Assertions.assertEquals(Address.PROFILE.substring(1), uri);
    }

    @Test
    void doGetWhenIdIsPresent() {
        log.info("GetProduct integration test");
        Mockito.when(request.getParameter(AttributeKeys.ID)).thenReturn("1");

        String uri = editProfile.doGet(request);
        Assertions.assertEquals("edit-profile", uri);
    }

    @Test
    void doPost() {
        log.info("CreateProduct integration test");
        String id = testUser.getId().toString();
        Mockito.when(request.getParameter(AttributeKeys.ID)).thenReturn(id);
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testUser);
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn("Bob");
        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn("bob007");
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn("asdf");

        int count = userService.getAll().size();
        String uri = editProfile.doPost(request);
        User updatedUser = userService.get(Long.parseLong(id)).orElse(null);
        if (updatedUser != null) {
            Assertions.assertEquals("Bob", updatedUser.getName());
            Assertions.assertEquals("bob007", updatedUser.getLogin());
            Assertions.assertEquals("asdf", updatedUser.getPassword());
        }
        Assertions.assertEquals("edit-profile?id=" + id, uri);
        Assertions.assertEquals(count, userService.getAll().size());
    }

    @Test
    void doPostForAdmin() {
        log.info("CreateProduct integration test");
        String id = testAdmin.getId().toString();
        Mockito.when(request.getParameter(AttributeKeys.ID)).thenReturn(id);
        Mockito.when(request.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(AttributeKeys.USER)).thenReturn(testAdmin);
        Mockito.when(request.getParameter(AttributeKeys.NAME)).thenReturn("Bob");
        Mockito.when(request.getParameter(AttributeKeys.LOGIN)).thenReturn("bob007");
        Mockito.when(request.getParameter(AttributeKeys.PASSWORD)).thenReturn("asdf");
        Mockito.when(request.getParameter(AttributeKeys.ROLE)).thenReturn(Role.SELLER.toString());

        int count = userService.getAll().size();
        String uri = editProfile.doPost(request);
        User updatedUser = userService.get(Long.parseLong(id)).orElse(null);
        if (updatedUser != null) {
            Assertions.assertEquals("Bob", updatedUser.getName());
            Assertions.assertEquals("bob007", updatedUser.getLogin());
            Assertions.assertEquals("asdf", updatedUser.getPassword());
        }
        Assertions.assertEquals("edit-profile?id=" + id, uri);
        Assertions.assertEquals(count, userService.getAll().size());
    }
}