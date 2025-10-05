package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.*;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.service.ProductStatisticsService;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
@AllArgsConstructor
public class ProductPage implements Command {

    private final ProductStatisticsService productStatisticsService;
    private final ProductService productService;

    @Override
    public String doGet(HttpServletRequest request) {
        if (request.getParameter("logout") == null) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            Long productId = Long.parseLong(request.getParameter("productId"));
            List<Store> stores = (List<Store>) session.getAttribute("stores");
            if (stores == null) {
                return Address.PROFILE;
            } else {
                for (Store store : stores) {
                    for (Product product : store.getProducts()) {
                        if (productId.equals(product.getId())) {
                            Product currentProduct = productService.get(productId)
                                    .orElseThrow(() -> new ApplicationException("Access denied or product was not found"));
                            Collection<ProductParameter> productParameters = currentProduct.getParameters();
                            int parametersSize = productParameters.size();
                            request.setAttribute("product", currentProduct);
                            request.setAttribute("parameters", productParameters);
                            request.setAttribute("size", parametersSize);
                        }
                    }
                }
                return Address.CARD;
            }
        } else {
            return Address.LOGOUT;
        }
    }
}
