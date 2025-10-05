package com.javarush.abdulkhanov.config;

import com.javarush.abdulkhanov.entity.*;
import com.javarush.abdulkhanov.service.ProductParameterService;
import com.javarush.abdulkhanov.service.ProductService;
import com.javarush.abdulkhanov.service.StoreService;
import com.javarush.abdulkhanov.service.UserService;
import com.javarush.abdulkhanov.utils.ProductCategory;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Config {
    private StoreService storeService;
    private UserService userService;
    private ProductService productService;
    private ProductParameterService productParameterService;

    public void fillEmptyRepositories() {
        setUsers();
        setStores();
        setProducts();
        createParametersForEveryProduct();
        setProductsToStores();

    }

    private void setProductsToStores() {
        List<Store> stores = (List<Store>) storeService.getAll();
        for (Store store : stores) {
            List<Product> products = (List<Product>) productService.getAll()
                    .stream()
                    .filter(product -> product.getSku().startsWith(getStoreAbbreviation(store.getStoreName())))
                    .toList();
            for (Product product : products) {
                fillProductParameters(product);
            }
            store.setProducts(products);
        }
    }

    private String getStoreAbbreviation(String storeName) {
        StringBuilder storeAbbreviation = new StringBuilder();
        char[] chars = storeName.toCharArray();
        boolean appendNext = false;
        for (char character : chars) {
            if (character == chars[0] || appendNext) {
                storeAbbreviation.append(character);
                appendNext = false;
            }
            if (character == ' ') {
                appendNext = true;
            }
        }
        return storeAbbreviation.toString().toLowerCase();
    }

    private void createParametersForEveryProduct() {
        List<Product> products = (List<Product>) productService.getAll();
        for (Product product : products) {
            product.setParameters(productParameterService.createListOfParameters(product.getCategory()));
        }
    }

    private void setProducts() {
        productService.create(buildProduct("Pupil's backpack", "cb1", 12L, ProductCategory.BACKPACK));
        productService.create(buildProduct("Men's backpack", "cb2", 10L, ProductCategory.BACKPACK));
        productService.create(buildProduct("Women's backpack", "cb3", 100L, ProductCategory.BACKPACK));
        productService.create(buildProduct("Backpack-transformer", "cb4", 50L, ProductCategory.BACKPACK));

        productService.create(buildProduct("Classic travel bag", "gb1", 100L, ProductCategory.TRAVEL_BAG));
        productService.create(buildProduct("Modern travel bag", "gb2", 50L, ProductCategory.TRAVEL_BAG));
        productService.create(buildProduct("Classic suitcase", "gb3", 20L, ProductCategory.SUITCASE));
        productService.create(buildProduct("Suitcase strap", "gb4", 20L, ProductCategory.ACCESSORIES));
    }

    private void setStores() {
        storeService.create(buildStore("Creative Backpacks", "1234"));
        storeService.create(buildStore("Granny's Bags", "qwerty"));
    }

    private void setUsers() {
        userService.create(buildUser("Seller1", "superseller1", "secret1", Role.SELLER, List.of("1234")));
        userService.create(buildUser("Seller2", "superseller2", "secret2", Role.SELLER, List.of("qwerty")));
        userService.create(buildUser("Admin", "admin", "admin", Role.ADMIN, List.of("1234", "qwerty")));
        userService.create(buildUser("HumanWithoutStore", "qwerty", "1234", Role.GUEST, List.of("")));
    }

    private void fillProductParameters(Product product) {
        List<ProductParameter> productParameters = (List<ProductParameter>) product.getParameters();
        for (ProductParameter productParameter : productParameters) {
            String productParameterName = productParameter.getName();
            String productParameterValue = getProductParameterValue(productParameterName);
            productParameter.setValue(productParameterValue);
        }
    }

    private static String getProductParameterValue(String productParameterName) {
        return switch (productParameterName) {
            case "price" -> "100";
            case "discount" -> "10";
            case "width" -> "30";
            case "length" -> "20";
            case "height" -> "15";
            case "weight" -> "250";
            case "color" -> "red";
            case "max volume" -> "40.0";
            case "handgrips" -> "true";
            case "shoulder strap" -> "true";
            case "exterior pockets" -> "2";
            case "interior pockets" -> "3";
            case "internal compartments" -> "2";
            case "anatomical back" -> "true";
            case "solid surface" -> "true";
            default -> throw new IllegalStateException("Unexpected value: " + productParameterName);
        };
    }

    private Product buildProduct(String name, String sku, Long amount, String category) {

        return Product.builder()
                .name(name)
                .sku(sku)
                .category(category)
                .totalAmount(amount)
                .build();
    }

    private Store buildStore(String storeName, String accessKey) {
        return Store.builder()
                .storeName(storeName)
                .accessKey(accessKey)
                .build();
    }

    private User buildUser(String name, String login, String password, Role role, List<String> sellersKeyList) {
        return User.builder()
                .name(name)
                .login(login)
                .password(password)
                .role(role)
                .sellerApiKeyList(sellersKeyList)
                .build();
    }

//    private Store buildStore(String storeName, String accessKey, List<Order> orders, List<Product> products) {
//        return Store.builder()
//                .storeName(storeName)
//                .accessKey(accessKey)
//                .orderList(orders)
//                .products(products)
//                .build();
//    }
}
