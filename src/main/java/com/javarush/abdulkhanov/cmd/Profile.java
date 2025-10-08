package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@SuppressWarnings("unused")
@AllArgsConstructor
public class Profile implements Command {

    StoreService storeService;

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<Store> stores = storeService.getAll();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeKeys.USER);
        if (user.getRole().equals(Role.ADMIN)) {
            session.setAttribute("stores", stores);
            log.info("All stores are available to user {}", user.getId());
            log.info("Admin entered");
            return Address.PROFILE.substring(1);
        }
        if (request.getParameter("logout") == null) {
            List<Store> userStores = new ArrayList<>();
            List<String> userStoreKeys = user.getSellerApiKeyList();
            List<Product> products = new ArrayList<>();
            if (userStoreKeys != null) {
                defineStoresAndProductsForUser(stores, userStoreKeys, userStores, products);
                session.setAttribute("products", products);
                session.setAttribute("stores", userStores);
            }
            return Address.PROFILE.substring(1);
        } else {
            log.info("Wrong access data");
            return Address.LOGOUT.substring(1);
        }
    }

    private void defineStoresAndProductsForUser(Collection<Store> stores, List<String> userStoreKeys,
                                                       List<Store> userStores, List<Product> products) {
        for (Store store : stores) {
            for (String storeKey : userStoreKeys) {
                if (store.getAccessKey().equals(storeKey)) {
                    userStores.add(store);
                    products.addAll(store.getProducts());
                }
            }
        }
    }

}
