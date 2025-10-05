package com.javarush.abdulkhanov.utils;

import com.javarush.abdulkhanov.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class RequestHelpers {

    public Long getId(HttpServletRequest req) {
        return getId(req, AttributeKeys.ID);
    }

    public Long getId(HttpServletRequest req, String key) {
        String id = req.getParameter(key);
        return id != null && !id.isBlank()
                ? Long.parseLong(id)
                : 0L;
    }

    public Long getId(HttpSession session) {
        Object user = session.getAttribute(AttributeKeys.USER);
        return user != null
                ? ((User) user).getId()
                : 0L;
    }

    public Optional<User> getUser(HttpSession session) {
        return Optional
                .ofNullable(session.getAttribute(AttributeKeys.USER))
                .map(User.class::cast); // equivalent to .map(u -> (User) u);
    }

    public static void createError(HttpServletRequest request, String errorMessage) {
        request.getSession().setAttribute(AttributeKeys.ERROR_MESSAGE, errorMessage);
    }
}
