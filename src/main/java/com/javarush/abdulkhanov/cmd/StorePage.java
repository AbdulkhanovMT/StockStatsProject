package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@SuppressWarnings("unused")
@AllArgsConstructor
public class StorePage implements Command {

    private StoreService storeService;

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeKeys.USER);
        List<String> userKeyList = user.getSellerApiKeyList();
        Long storeId = Long.parseLong(request.getParameter("storeId"));
        Store store = storeService.get(storeId)
                .orElseThrow(() -> new ApplicationException("Access denied or store was not found"));
        request.setAttribute("store", store);
        if (userKeyList != null && userKeyList.contains(store.getAccessKey())) {
            log.info("Store was successfully accessed.");
            return Address.STORE_PAGE;
        } else {
            log.info("Store was not accessed.");
            return Address.PROFILE;
        }
    }
}
