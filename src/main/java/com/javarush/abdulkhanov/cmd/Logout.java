package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Logout implements Command {
    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return Address.LOGIN;
    }
}
