package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.webapp.servlets.ContextHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateAccountViewServlet extends ContextHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(req.getParameter("id"));
        Account account = accountService.get(id);
        req.setAttribute("account", account);
        req.getRequestDispatcher("updateAccount.jsp").forward(req, resp);
    }
}
