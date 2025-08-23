package com.javarush.abdulkhanov.controller;

import com.javarush.abdulkhanov.cmd.Command;
import com.javarush.abdulkhanov.config.ServiceLocator;
import jakarta.servlet.http.HttpServletRequest;

import static java.lang.Class.*;

public class CommandIdentifier {

    public Command identifier(HttpServletRequest req) {
        try {
            String uri = req.getRequestURI().equals("/")
                    ? "/start"
                    : req.getRequestURI();
            String kebabName = uri.split("[/#?]")[1];
            String commandSimpleName = kebabStyleToCamelCase(kebabName);
            String fullName = Command.class.getPackageName() + "." + commandSimpleName;
            return (Command) ServiceLocator.find(forName(fullName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String kebabStyleToCamelCase(String kebabStyle) {
        char[] kebabChars = kebabStyle.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean capitalize = true;
        for (char c : kebabChars) {
            if (c == '-') {
                capitalize = true;
            } else if (capitalize) {
                builder.append(Character.toUpperCase(c));
                capitalize = false;
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
