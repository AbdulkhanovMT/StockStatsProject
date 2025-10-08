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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
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
        if(!(name.isBlank() || sku.isBlank() || category.isBlank() || totalAmount.isBlank())) {
            productService.create(Product.builder()
                    .name(name)
                    .sku(sku)
                    .category(category)
                    .totalAmount(Long.parseLong(totalAmount))
                    .build());
            log.info("Product created successfully");
        }
        Product product = productService.get(name, sku).orElseThrow(
                () -> {
                    log.warn("Product with name {} and sku {} not found", name, sku);
                    return new ApplicationException("Product not found");
                }
        );
        req.setAttribute("product", product);
        Long productId = product.getId();
        imageService.uploadImage(req, product.getImage());
        log.info("Product was created. Forwarded to fill parameters.");
        return Address.FILL_PRODUCT_PARAMETERS + "?category=" + category + "&storeId=" + storeId + "&productId=" + product.getId();
    }
}