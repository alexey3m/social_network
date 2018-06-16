package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.Role;
import com.getjavajob.training.web1803.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int actionId = Integer.valueOf(request.getParameter("actionId"));
        AccountService service = new AccountService();
        String message = "updateRoleFalse";
        if (action.equals("toUser")) {
            service.updateRole(actionId, Role.USER);
            message = "updateRoleUser";
        } else if (action.equals("toAdmin")) {
            service.updateRole(actionId, Role.ADMIN);
            message = "updateRoleAdmin";
        }
        response.sendRedirect("account.jsp?id=" + actionId + "&message=" + message);
    }
}