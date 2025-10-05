package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.ProductParameter;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.service.ImageService;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@SuppressWarnings("unused")
@AllArgsConstructor
public class CreateProduct implements Command {

    private final ProductService productService;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        String storeId = req.getParameter("storeId");
        String name = req.getParameter("name");
        String sku = req.getParameter("sku");
        String category = req.getParameter("category");
        String totalAmount = req.getParameter("totalAmount");
        productService.create(Product.builder()
                .name(name)
                .sku(sku)
                .category(category)
                .totalAmount(Long.parseLong(totalAmount))
                .build());
        Product product = productService.get(name, sku).orElse(null);
        if (product != null) {
            req.setAttribute("product", product);
            Long productId = product.getId();
            imageService.uploadImage(req, product.getImage());
            return Address.FILL_PRODUCT_PARAMETERS + "?category=" + category + "&storeId=" + storeId + "&productId=" + productId;
        }
        return Address.CREATE_PRODUCT;
    }
}