package com.getjavajob.training.web1803.webapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("id");
        session.removeAttribute("userName");
        session.removeAttribute("role");
        session.invalidate();

        Cookie cookieUsername = new Cookie("email", null);
        cookieUsername.setMaxAge(0);
        cookieUsername.setPath("/");
        Cookie cookiePassword = new Cookie("password", null);
        cookiePassword.setMaxAge(0);
        cookiePassword.setPath("/");
        Cookie cookieId = new Cookie("id", null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
        response.addCookie(cookieId);
        response.sendRedirect("login.jsp");
    }
}
