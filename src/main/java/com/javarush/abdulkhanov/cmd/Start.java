package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Start implements Command {
    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Role role = RequestHelpers.getUser(session).map(User::getRole).orElse(Role.GUEST);
        if(role == Role.GUEST){
            return getView();
        } else{
            return Address.PROFILE.substring(1);
        }

    }
}
