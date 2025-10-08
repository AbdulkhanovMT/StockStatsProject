package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@SuppressWarnings("unused")
@AllArgsConstructor
public class ListUser implements Command {

    private final UserService userService;

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<User> users = userService.getAll();
        request.setAttribute("users", users);
        log.info("List of users was shown");
        return getView();
    }


}