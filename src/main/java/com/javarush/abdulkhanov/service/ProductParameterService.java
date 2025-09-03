package com.javarush.abdulkhanov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.abdulkhanov.entity.ProductParameter;
import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.repository.ProductParameterRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ProductParameterService {
    private ProductParameterRepository productParameterRepository;

    private static final ObjectMapper mapper = new JsonMapper();
    private static Map<String, Map<String, String>> categorizedProductParameters = new LinkedHashMap<>();
    private static final InputStream productParametersJsonStream = ProductService.class
            .getResourceAsStream("/product-parameters.json");

    static {
        getProductParameters(productParametersJsonStream);
    }

    private static void getProductParameters(InputStream resourceAsStream) {
        try {
            categorizedProductParameters = mapper.readValue(resourceAsStream, Map.class);
        } catch (IOException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public ProductParameterService(ProductParameterRepository productParameterRepository) {
        this.productParameterRepository = productParameterRepository;
    }

    public Collection<ProductParameter> getAll(){
        return productParameterRepository.getAll();
    }

    public Optional<ProductParameter> get(Long id){
        return Optional.ofNullable(productParameterRepository.get(id));
    }

    public Optional<ProductParameter> get(String name){
        ProductParameter parameter = ProductParameter.builder()
                .name(name)
                .build();
        return productParameterRepository.find(parameter).findAny();
    }

    public void create(ProductParameter productParameter){
        ProductParameter pattern = ProductParameter.builder()
                .name(productParameter.getName())
                .build();
        if(productParameterRepository.find(pattern).findAny().isPresent()){
            productParameterRepository.create(productParameter);
        }
    }

    public void update(ProductParameter productParameter){
        productParameterRepository.update(productParameter);
    }

    public void delete(ProductParameter productParameter){
        productParameterRepository.delete(productParameter);
    }

    public List<ProductParameter> createListOfParameters(String productCategory){
        Map<String, String> parametersMap = categorizedProductParameters.get(productCategory);
        Set<String> parametersNameSet = parametersMap.keySet();
        List<ProductParameter> parameters = new ArrayList<>();
        for (String parameterName : parametersNameSet) {
            parameters.add(ProductParameter.builder()
                    .name(parameterName)
                    .typeOfParameter(parametersMap.get(parameterName))
                    .build());
        }
        return parameters;
    }

}
