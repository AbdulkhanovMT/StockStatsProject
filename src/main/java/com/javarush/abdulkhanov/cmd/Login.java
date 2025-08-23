package com.javarush.abdulkhanov.cmd;

import jakarta.servlet.http.HttpServletRequest;

public class Login implements Command{
    @Override
    public String doGet(HttpServletRequest request) {
        return this.getView();
    }
}
