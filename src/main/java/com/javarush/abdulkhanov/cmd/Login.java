package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class Login implements Command {

    private final UserService userService;

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter(AttributeKeys.LOGIN);
        String password = request.getParameter(AttributeKeys.PASSWORD);
        Optional<User> user = userService.get(login, password);
        if (user.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute(AttributeKeys.USER, user.get());
            log.info("Login successful");
            return Address.PROFILE;
        } else {
            log.info("Wrong access data");
            return Address.LOGIN;
        }
    }
}
