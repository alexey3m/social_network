package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.webapp.servlets.ContextHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchResultViewServlet extends ContextHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String searchString = req.getParameter("inputSearch");
        List<Account> findAccounts = accountService.searchByString(searchString);
        List<Group> findGroups = groupService.searchByString(searchString);
        req.setAttribute("searchString", searchString);
        req.setAttribute("findAccounts", findAccounts);
        req.setAttribute("findGroups", findGroups);
        req.getRequestDispatcher("searchResult.jsp").forward(req, resp);
    }
}