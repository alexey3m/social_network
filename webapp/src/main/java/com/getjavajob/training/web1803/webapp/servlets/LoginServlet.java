package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;

import javax.servlet.http.*;
import java.io.IOException;


public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = null;
        String password = null;
        boolean useCookies = Boolean.parseBoolean(request.getParameter("cookie"));
        if (useCookies) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals("email")) {
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
        AccountService service = new AccountService();
        try {
            int id = service.loginAndGetId(email, password);
            Account currentAccount = service.get(id);
            HttpSession session = request.getSession(true);
            session.setAttribute("email", email);
            session.setAttribute("id", id);
            session.setAttribute("userName", currentAccount.getFirstName() + " " + currentAccount.getLastName());
            session.setAttribute("role", currentAccount.getRole());
            String rememberMeActive = request.getParameter("rememberMe");
            if ("active".equals(rememberMeActive)) {
                Cookie cookieUsername = new Cookie("email", email);
                Cookie cookiePassword = new Cookie("password", password);
                Cookie cookieId = new Cookie("id", String.valueOf(id));
                response.addCookie(cookieUsername);
                response.addCookie(cookiePassword);
                response.addCookie(cookieId);
            }
            response.sendRedirect("account.jsp?id=" + id);
        } catch (DaoNameException e) {
            response.sendRedirect("login.jsp?message=alert");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }
}
