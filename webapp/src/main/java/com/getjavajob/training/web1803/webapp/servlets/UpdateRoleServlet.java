package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateRoleServlet extends ContextHttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int actionId = Integer.valueOf(request.getParameter("actionId"));
        String message = "updateRoleFalse";
        if (action.equals("toUser")) {
            accountService.updateRole(actionId, Role.USER);
            message = "updateRoleUser";
        } else if (action.equals("toAdmin")) {
            accountService.updateRole(actionId, Role.ADMIN);
            message = "updateRoleAdmin";
        }
        response.sendRedirect("AccountViewServlet?id=" + actionId + "&infoMessage=" + message);
    }
}