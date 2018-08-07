package com.getjavajob.training.web1803.webapp.security;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SocNetAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private AccountService accountService;

    @Autowired
    public SocNetAuthenticationSuccessHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public SocNetAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) authentication.getPrincipal();
        Account currentAccount = new Account();
        String email = user.getUsername();
        try {
            currentAccount = accountService.getByEmail(email);
        } catch (DaoNameException e) {
            e.printStackTrace();
        }
        int id = currentAccount.getId();
        session.setAttribute("email", email);
        session.setAttribute("id", id);
        session.setAttribute("userName", currentAccount.getFirstName() + " " + currentAccount.getLastName());
        session.setAttribute("role", currentAccount.getRole());
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/viewAccount?id=" + id);
    }
}