package com.getjavajob.training.web1803.webapp.servlets;

import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("id");
        session.removeAttribute("userName");
        session.removeAttribute("role");
        session.invalidate();
        Cookie email = new Cookie("email", null);
        email.setMaxAge(0);
        email.setPath("/");
        Cookie cookiePassword = new Cookie("password", null);
        cookiePassword.setMaxAge(0);
        cookiePassword.setPath("/");
        Cookie cookieId = new Cookie("id", null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(email);
        response.addCookie(cookiePassword);
        response.addCookie(cookieId);
        response.sendRedirect("login.jsp");
    }
}
