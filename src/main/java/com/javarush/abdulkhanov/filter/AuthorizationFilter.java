package com.javarush.abdulkhanov.filter;

import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.RequestHelpers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@WebFilter({
        Address.START, Address.CARD, Address.CREATE_PRODUCT,
        Address.INDEX, Address.EDIT_PROFILE, Address.LIST_USER,
        Address.STORE_PAGE, Address.PROFILE, Address.LOGIN,
        Address.LOGOUT, Address.SIGNUP, Address.STAT,
        Address.INFO, Address.LIST_OF_PRODUCTS, Address.LIST_STORE,
        Address.EDIT_PRODUCT, Address.FILL_PRODUCT_PARAMETERS
})
public class AuthorizationFilter extends HttpFilter {

    Map<Role, List<String>> permissionsMap = Map.of(
            Role.GUEST, List.of(
                    Address.START, Address.LOGIN, Address.SIGNUP
            ),
            Role.USER, List.of(
                    Address.START, Address.LOGIN, Address.SIGNUP,
                    Address.EDIT_PROFILE, Address.PROFILE, Address.INFO, Address.LOGOUT
            ),
            Role.SELLER, List.of(
                    Address.START, Address.LOGIN, Address.SIGNUP,
                    Address.STORE_PAGE, Address.PROFILE, Address.LOGOUT,
                    Address.CARD, Address.CREATE_PRODUCT, Address.EDIT_PROFILE, Address.LIST_STORE,
                    Address.EDIT_PRODUCT, Address.FILL_PRODUCT_PARAMETERS
            ),
            Role.ADMIN, List.of(
                    Address.START, Address.LOGIN, Address.SIGNUP,
                    Address.STORE_PAGE, Address.PROFILE, Address.LOGOUT,
                    Address.CARD, Address.CREATE_PRODUCT, Address.EDIT_PROFILE,
                    Address.LIST_USER, Address.LIST_STORE,
                    Address.EDIT_PRODUCT, Address.FILL_PRODUCT_PARAMETERS
            )
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        requestURI = requestURI.equals("/") ? "/start" : requestURI;
        String command = requestURI.split("[?#&/]")[1];
        HttpSession session = req.getSession();
        Role role = RequestHelpers.getUser(session)
                .map(User::getRole)
                .orElse(Role.GUEST);
        if (permissionsMap.get(role).contains("/"+command)) {
            chain.doFilter(req, res);
        } else {
            String message = "Access denied";
            log.warn(message);
            RequestHelpers.createError(req, message);
            res.sendRedirect(Address.START);
        }
    }
}
