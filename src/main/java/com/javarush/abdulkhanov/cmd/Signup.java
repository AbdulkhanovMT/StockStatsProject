package com.javarush.abdulkhanov.cmd;

import jakarta.servlet.http.HttpServletRequest;

public class Signup implements Command{
    @Override
    public String doGet(HttpServletRequest request) {
        return this.getView();
    }
}
