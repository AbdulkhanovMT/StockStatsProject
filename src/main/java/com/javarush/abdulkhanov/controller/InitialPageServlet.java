package com.javarush.abdulkhanov.controller;

import com.javarush.abdulkhanov.cmd.Command;
import com.javarush.abdulkhanov.config.Config;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.utils.Address;
import com.javarush.abdulkhanov.utils.ProductCategory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet({Address.START, Address.CARD, Address.CREATE_PRODUCT,
        Address.INDEX, Address.EDIT_PROFILE, Address.LIST_USER,
        Address.STORE_PAGE, Address.PROFILE, Address.LOGIN,
        Address.LOGOUT, Address.SIGNUP, Address.STAT,
        Address.INFO, Address.LIST_OF_PRODUCTS,
        Address.LIST_STORE, Address.EDIT_PRODUCT, Address.FILL_PRODUCT_PARAMETERS
})
public class InitialPageServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        Config config = ServiceLocator.find(Config.class);
        config.fillEmptyRepositories();

        ServletContext servletContext = servletConfig.getServletContext();
        servletContext.setAttribute("roles", Role.values());

        servletContext.setAttribute("categories", List.of(ProductCategory.BACKPACK,
                ProductCategory.SUITCASE,  ProductCategory.TRAVEL_BAG,
                ProductCategory.BANANA_BAG, ProductCategory.ACCESSORIES));
    }

    private final CommandIdentifier commandIdentifier = new CommandIdentifier();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandIdentifier.identifier(req);
        String view = command.doGet(req);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

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
