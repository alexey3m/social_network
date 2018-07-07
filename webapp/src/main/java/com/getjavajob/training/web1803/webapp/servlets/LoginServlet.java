package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends ContextHttpServlet {
    private static final String EMAIL = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String email = null;
        String password = null;
        boolean useCookies = Boolean.parseBoolean(request.getParameter("cookie"));
        if (useCookies) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(EMAIL)) {
                    email = cookie.getValue();
                }
                if (cookieName.equals("password")) {
                    password = cookie.getValue();
                }
            }
        } else {
            email = request.getParameter("inputEmail");
            password = request.getParameter("inputPassword");
        }
        try {
            int id = accountService.loginAndGetId(email, password);
            Account currentAccount = accountService.get(id);
            HttpSession session = request.getSession(true);
            session.setAttribute(EMAIL, email);
            session.setAttribute("id", id);
            session.setAttribute(EMAIL, email);
            session.setAttribute("userName", currentAccount.getFirstName() + " " + currentAccount.getLastName());
            session.setAttribute("role", currentAccount.getRole());
            String rememberMeActive = request.getParameter("rememberMe");
            if ("active".equals(rememberMeActive)) {
                Cookie cookieUsername = new Cookie(EMAIL, email);
                Cookie cookiePassword = new Cookie("password", password);
                Cookie cookieId = new Cookie("id", String.valueOf(id));
                response.addCookie(cookieUsername);
                response.addCookie(cookiePassword);
                response.addCookie(cookieId);
            }
            response.sendRedirect("AccountViewServlet?id=" + id);
        } catch (DaoNameException e) {
            response.sendRedirect("login.jsp?infoMessage=alert");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }
}
