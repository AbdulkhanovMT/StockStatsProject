package com.javarush.abdulkhanov.service;

import com.javarush.abdulkhanov.exception.ApplicationException;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> get(Long id){
        return Optional.of(userRepository.get(id));
    }

    public Optional<User> get(String login, String password) {
        User possibleUser = User.builder()
                .login(login)
                .password(password)
                .build();
        return userRepository.find(possibleUser).findAny();
    }

    public void create(User user){
        User possibleUser = User.builder().login(user.getLogin()).build();
        if (userRepository.find(possibleUser).findAny().isEmpty()) {
            userRepository.create(user);
        } else {
            throw new ApplicationException("User with login " + user.getLogin() + " already exists");
        }
    }

    public void update(User user){userRepository.update(user);}

    public void delete(User user){userRepository.delete(user);}
}
