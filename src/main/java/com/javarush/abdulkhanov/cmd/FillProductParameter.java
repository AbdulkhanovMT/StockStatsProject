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
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
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
        log.info("Product parameters were created");
        String productId = req.getParameter("productId");
        if (productId != null) {
            long id = Long.parseLong(productId);
            productService.get(id)
                    .ifPresent(product -> {
                        log.info("Product was found with id {}", product.getId());
                        req.setAttribute("product", product);
                        product.setParameters(parameters);
                        log.info("Product parameters were set to product {}", product.getId());
                    });
        }
        log.info("Product parameters filling page is shown");
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
            log.warn("Product not found with id {}", stringId);
            return Address.CREATE_PRODUCT;
        }
        Product product = optional.get();
        Map<String, ProductParameter> parameters = fillProductParameterMap(req, product);
        if (parameters.isEmpty()) {
            productService.delete(product);
            log.warn("Parameters were not filled");
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
        log.info("Product {} was updated", createdProduct.getId());
        Store store = storeService.get(Long.parseLong(storeId)).get();
        List<Product> updatedList = new ArrayList<>(store.getProducts());
        updatedList.add(createdProduct);
        log.info("Product {} was added to store {}", createdProduct.getId(), store.getId());
        store.setProducts(updatedList);
        log.info("New product {} was created and added to store {}. Forward to the product page.", createdProduct.getId(), store.getId());
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