package com.javarush.abdulkhanov.entity;

public class User {
    private final Role role;

    public User(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
