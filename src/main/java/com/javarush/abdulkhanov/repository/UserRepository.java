package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.User;

import java.util.Objects;
import java.util.stream.Stream;

public class UserRepository extends AbstractRepository<User> {

    @Override
    public Stream<User> find(User entity) {
        return entities.values().stream()
                .filter(user -> Objects.equals(user.getId(), entity.getId()))
                .filter(user -> user.getLogin().equals(entity.getLogin()))
                .filter(user -> user.getPassword().equals(entity.getPassword()))
                .filter(user -> user.getRole().equals(entity.getRole()));
    }
}
