package com.javarush.abdulkhanov.controller;

import com.javarush.abdulkhanov.cmd.Command;
import com.javarush.abdulkhanov.utils.Address;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet({Address.START, Address.CARD, Address.CREATE,
        Address.INDEX, Address.EDIT_USER, Address.LIST_USER,
        Address.OPEN_CARD, Address.PROFILE, Address.LOGIN,
        Address.LOGOUT, Address.SIGNUP, Address.STAT})
public class InitialPageServlet extends HttpServlet {

    private final CommandIdentifier commandIdentifier = new CommandIdentifier();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandIdentifier.identifier(req);
        String view = command.getView();
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

//    @Override
//    public void init(ServletConfig servletConfig) {
//        Config config = Winter.find(Config.class);
//        config.fillEmptyRepository();
//
//        ServletContext servletContext = servletConfig.getServletContext();
//        servletContext.setAttribute("roles", Role.values());
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = commandIdentifier.identifier(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }

    private String getJsp(String view) {
        return "/WEB-INF/" + view + ".jsp";
    }
}
