package com.javarush.abdulkhanov.filter;

import com.javarush.abdulkhanov.utils.AttributeKeys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class ErrorRemoveFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);
        HttpSession session = req.getSession(false);
        if (req.getMethod().equals("GET") && session != null) {
            session.removeAttribute(AttributeKeys.ERROR_MESSAGE);
        }
    }
}
