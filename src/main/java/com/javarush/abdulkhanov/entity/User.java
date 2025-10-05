package com.javarush.abdulkhanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements AbstractEntity {
    private Role role;
    private Long id;
    private String name;
    private List<String> sellerApiKeyList = new ArrayList<>();
    private String login;
    private String password;

    public String getImage(){
        return "user-" + this.getId();
    }
}

