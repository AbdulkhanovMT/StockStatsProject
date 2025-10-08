package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.service.ProductStatisticsService;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
@AllArgsConstructor
public class ProductList implements Command {

    private ProductStatisticsService productStatisticsService;
    private ProductService productService;

    @Override
    public String doGet(HttpServletRequest request) {
        if (request.getParameter("logout") == null) {
            HttpSession session = request.getSession();
            session.setAttribute("products", productService.getAll());
            return Address.LIST_OF_PRODUCTS;
        } else {
            return Address.LOGOUT;
        }
    }
}
