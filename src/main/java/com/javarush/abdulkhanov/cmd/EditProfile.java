package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.ImageService;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;


@SuppressWarnings("unused")
@AllArgsConstructor
public class EditProfile implements Command {

    private final UserService userService;
    private final ImageService imageService;

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(AttributeKeys.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            userService.get(id)
                    .ifPresent(user -> req.setAttribute(AttributeKeys.USER, user));
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter(AttributeKeys.ID));
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(AttributeKeys.USER);
        Role currentRole = currentUser.getRole();
        User user = User.builder()
                .id(id)
                .name(req.getParameter(AttributeKeys.NAME))
                .login(req.getParameter(AttributeKeys.LOGIN))
                .password(req.getParameter(AttributeKeys.PASSWORD))
                .role((currentRole==Role.ADMIN)?Role.valueOf(req.getParameter(AttributeKeys.ROLE)):currentRole)
                .build();
        userService.update(user);
        imageService.uploadImage(req, user.getImage());
        return getView() + "?id=" + user.getId();
    }
}