package com.javarush.abdulkhanov;

import com.javarush.abdulkhanov.config.Config;
import com.javarush.abdulkhanov.config.ServiceLocator;
import com.javarush.abdulkhanov.entity.Role;
import com.javarush.abdulkhanov.entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseIT {
    protected HttpSession mockedSession;
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final Config config;
    protected final ServletConfig servletConfig;
    protected final ServletContext servletContext;
    protected User testAdmin;
    protected User testSeller;
    protected User testUser;
    protected User testGuest;

    public BaseIT(){
        //app config
        config = ServiceLocator.find(Config.class);
        config.fillEmptyRepositories();
        //servlet config
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        //current op
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        mockedSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockedSession);
        //test data
        testAdmin = User.builder()
                .id(1L)
                .login("testAdmin")
                .password("testAdmin")
                .role(Role.ADMIN)
                .build();
        testSeller = User.builder()
                .id(2L)
                .login("testSeller")
                .password("testSeller")
                .sellerApiKeyList(List.of("1234"))
                .role(Role.SELLER)
                .build();
        testUser = User.builder()
                .id(3L)
                .login("testUser")
                .password("testUser")
                .role(Role.USER)
                .build();
        testGuest = User.builder()
                .id(4L)
                .login("testGuest")
                .password("testGuest")
                .role(Role.GUEST)
                .build();
    }
}
