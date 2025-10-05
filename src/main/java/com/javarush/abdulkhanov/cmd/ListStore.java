package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.util.List;

@SuppressWarnings({"unused", "unchecked"})
@AllArgsConstructor
public class ListStore implements Command {

    private final StoreService storeService;

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Store> sessionStores = (List<Store>) session.getAttribute("stores");

        return getView();
    }


}