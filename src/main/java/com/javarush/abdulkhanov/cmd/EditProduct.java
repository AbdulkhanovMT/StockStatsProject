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
public class EditProduct implements Command {

    private final ProductService productService;
    private final ImageService imageService;

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter("productId");
        HttpSession session = req.getSession();
        Product currentProduct = productService.get(Long.valueOf(stringId))
                .orElseThrow(()->new ApplicationException("Product was not found"));
        Collection<ProductParameter> currentParameters = currentProduct.getParameters();
        req.setAttribute("parameters", currentParameters);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            productService.get(id)
                    .ifPresent(product -> req.setAttribute("product", product));
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("productId"));
        HttpSession session = req.getSession();
        Product currentProduct = productService.get(id)
                .orElseThrow(()->new ApplicationException("Product was not found"));
        Collection<ProductParameter> currentParameters = currentProduct.getParameters();
        List<ProductParameter> parameters = new ArrayList<>();
        for (ProductParameter p : currentParameters) {
            ProductParameter parameter = ProductParameter.builder()
                    .name(p.getName())
                    .value(req.getParameter(p.getName()))
                    .build();
            parameters.add(parameter);
        }
        Product product = Product.builder().id(id)
                .name(currentProduct.getName())
                .sku(currentProduct.getSku())
                .category(currentProduct.getCategory())
                .totalAmount(Long.valueOf(req.getParameter("totalAmount")))
                .parameters(parameters)
                .build();
        productService.update(product);
        imageService.uploadImage(req, product.getImage());
        return Address.CARD + "?productId=" + product.getId();
    }
}