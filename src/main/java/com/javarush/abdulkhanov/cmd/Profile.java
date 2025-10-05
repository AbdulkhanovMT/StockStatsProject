package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Profile implements Command {

    StoreService storeService;
    UserService userService;

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<Store> stores = storeService.getAll();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getRole().equals(Role.ADMIN)) {
            session.setAttribute("stores", stores);
            return Address.PROFILE.substring(1);
        }
        if (request.getParameter("logout") == null) {
            List<Store> userStores = new ArrayList<>();
            List<String> userStoreKeys = user.getSellerApiKeyList();
            List<Product> products = new ArrayList<>();
            if (userStoreKeys != null) {
                for (Store store : stores) {
                    for (String storeKey : userStoreKeys) {
                        if (store.getAccessKey().equals(storeKey)) {
                            userStores.add(store);
                            products.addAll(store.getProducts());
                        }
                    }
                }
                session.setAttribute("products", products);
                session.setAttribute("stores", userStores);
            }
            return Address.PROFILE.substring(1);
        } else {
            return Address.LOGOUT.substring(1);
        }
    }

}
