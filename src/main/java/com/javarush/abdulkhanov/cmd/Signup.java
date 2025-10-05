package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.ImageService;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Signup implements Command {

    private final UserService userService;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest request) {
        if(!request.getParameter(AttributeKeys.PASSWORD).equals(request.getParameter("repeatPass"))){
            return Address.SIGNUP;
        }
        User user = User.builder()
                .name(request.getParameter(AttributeKeys.NAME))
                .login(request.getParameter(AttributeKeys.LOGIN))
                .password(request.getParameter(AttributeKeys.PASSWORD))
                .role(Role.USER)
                .build();
        userService.create(user);
        imageService.uploadImage(request, user.getImage());
        HttpSession session = request.getSession();
        session.setAttribute(AttributeKeys.USER, user);
        return Address.PROFILE;
    }
}
