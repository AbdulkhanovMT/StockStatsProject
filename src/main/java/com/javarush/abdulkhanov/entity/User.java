package com.javarush.abdulkhanov.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
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

