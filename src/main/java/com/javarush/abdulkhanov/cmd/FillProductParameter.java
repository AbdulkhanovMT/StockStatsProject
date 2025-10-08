package com.javarush.abdulkhanov.cmd;

import com.javarush.abdulkhanov.entity.Product;
import com.javarush.abdulkhanov.entity.ProductParameter;
import com.javarush.abdulkhanov.entity.Store;
import com.javarush.abdulkhanov.service.ImageService;
import com.javarush.abdulkhanov.service.ProductParameterService;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;


@SuppressWarnings("unused")
@AllArgsConstructor
public class FillProductParameter implements Command {

    private final ProductParameterService productParameterService;
    private final ProductService productService;
    private final StoreService storeService;

    @Override
    public String doGet(HttpServletRequest req) {
        String storeId = req.getParameter("storeId");
        String category = req.getParameter("category");
        List<ProductParameter> parameters = productParameterService.createListOfParameters(category);
        req.setAttribute("parameters", parameters);
        String productId = req.getParameter("productId");
        if (productId != null) {
            long id = Long.parseLong(productId);
            productService.get(id)
                    .ifPresent(product -> {
                        req.setAttribute("product", product);
                        product.setParameters(parameters);
                    });
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        String storeId = req.getParameter("storeId");
        String stringId = req.getParameter("productId");
        long id = Long.parseLong(stringId);
        Optional<Product> optional = productService.get(id);
        if (optional.isEmpty()) {
            return Address.CREATE_PRODUCT;
        }
        Product product = optional.get();
        Map<String, ProductParameter> parameters = fillProductParameterMap(req, product);
        if (parameters.isEmpty()) {
            productService.delete(product);
            return Address.CREATE_PRODUCT;
        }
        Product createdProduct = Product.builder().id(id)
                .name(product.getName())
                .sku(product.getSku())
                .category(product.getCategory())
                .totalAmount(product.getTotalAmount())
                .parameters(parameters.values())
                .build();
        productService.update(createdProduct);
        Store store = storeService.get(Long.parseLong(storeId)).get();
        List<Product> updatedList = new ArrayList<>(store.getProducts());
        updatedList.add(createdProduct);
        store.setProducts(updatedList);
        return Address.CARD + "?productId=" + id;
    }

    private Map<String, ProductParameter> fillProductParameterMap(HttpServletRequest req, Product product) {
        Map<String, ProductParameter> parameters = new HashMap<>();
        List<ProductParameter> currentParameters = (List<ProductParameter>) product.getParameters();
        if (currentParameters == null) {
            return parameters;
        }
        for (ProductParameter p : currentParameters) {
            parameters.put(p.getName(), p);
        }
        for (ProductParameter p : currentParameters) {
            ProductParameter parameter = ProductParameter.builder()
                    .name(p.getName())
                    .value(req.getParameter(p.getName()))
                    .build();
            parameters.put(parameter.getName(), parameter);
        }
        return parameters;
    }
}