package com.getjavajob.training.web1803.webapp.filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie item : cookies) {
                cookieMap.put(item.getName(), item);
            }
        }
        boolean loggedIn = session != null && session.getAttribute("email") != null;
        boolean cookieExists = cookieMap.get("email") != null;
        if (request.getServletPath().equals("/login.jsp") || request.getServletPath().equals("/reg.jsp")) {
            chain.doFilter(request, response);
        } else if (!loggedIn && cookieExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoginServlet?cookie=true");
            dispatcher.forward(request, response);
        } else if (!loggedIn || request.getServletPath().equals("/index.jsp")) {
            response.sendRedirect("login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) {

    }

}
