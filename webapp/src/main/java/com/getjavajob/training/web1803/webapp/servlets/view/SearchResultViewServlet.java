package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchResultViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("inputSearch");
        AccountService accountService = new AccountService();
        GroupService groupService = new GroupService();
        List<Account> findAccounts = accountService.searchByString(searchString);
        List<Group> findGroups = groupService.searchByString(searchString);
        req.setAttribute("searchString", searchString);
        req.setAttribute("findAccounts", findAccounts);
        req.setAttribute("findGroups", findGroups);
        req.getRequestDispatcher("searchResult.jsp").forward(req, resp);
    }
}