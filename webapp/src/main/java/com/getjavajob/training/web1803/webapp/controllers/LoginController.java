package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static final String EMAIL = "email";

    private AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/loginUser", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(@RequestParam(required = false, name = "cookie") Boolean useCookies,
                        @RequestParam(required = false, name = "rememberMe") Boolean rememberMe,
                        @RequestParam(required = false, name = "inputEmail") String email,
                        @RequestParam(required = false, name = "inputPassword") String password,
                        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (useCookies != null) {
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
        }
        try {
            int id = accountService.loginAndGetId(email, password);
            Account currentAccount = accountService.get(id);
            session.setAttribute(EMAIL, email);
            session.setAttribute("id", id);
            session.setAttribute("userName", currentAccount.getFirstName() + " " + currentAccount.getLastName());
            session.setAttribute("role", currentAccount.getRole());
            if (rememberMe != null) {
                Cookie cookieUsername = new Cookie(EMAIL, email);
                Cookie cookiePassword = new Cookie("password", password);
                Cookie cookieId = new Cookie("id", String.valueOf(id));
                response.addCookie(cookieUsername);
                response.addCookie(cookiePassword);
                response.addCookie(cookieId);
            }
            return "redirect:viewAccount?id=" + id;
        } catch (DaoNameException e) {
            return "redirect:/jsp/login.jsp?infoMessage=alert";
        }
    }

    @RequestMapping("/logoutUser")
    public String logoutUser(HttpSession session, HttpServletResponse response) {
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
        return "/jsp/login.jsp";
    }
}