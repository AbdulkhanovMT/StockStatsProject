package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Start implements Command {
    @Override
    public String doGet(HttpServletRequest request) {
//        HttpSession session = request.getSession(true);
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return getView();
//        }
//        return getView() + "-authorised";
        return getView();
    }
}
