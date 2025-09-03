package com.javarush.abdulkhanov.entity;

public interface AbstractEntity {
    Long getId();
    void setId(Long id);
    default String getImage(){
        return this.getClass().getSimpleName().toLowerCase() + this.getId();
    }
}
