package com.getjavajob.training.web1803.webapp.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie item : cookies) {
                cookieMap.put(item.getName(), item);
            }
        }
        String uri = request.getRequestURI();
        boolean loggedIn = session != null && session.getAttribute("email") != null;
        boolean cookieExists = cookieMap.get("email") != null;
        if (uri.endsWith("/loginUser") || uri.endsWith("/regPage") || uri.endsWith("/reg")) {
            return true;
        } else if (!loggedIn && cookieExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginUser?cookie=true");
            dispatcher.forward(request, response);
            return true;
        } else if (!loggedIn) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
            dispatcher.forward(request, response);
            response.sendRedirect("/jsp/login.jsp");
            return true;
        } else {
            return true;
        }
    }
}