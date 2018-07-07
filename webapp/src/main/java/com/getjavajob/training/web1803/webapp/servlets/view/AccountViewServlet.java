package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.webapp.servlets.ContextHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountViewServlet extends ContextHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(req.getParameter("id"));
        String actionIdString = req.getParameter("actionId");
        int actionId = actionIdString != null ? Integer.valueOf(actionIdString) : 0;
        String infoMessage = req.getParameter("infoMessage");
        HttpSession session = req.getSession(true);
        int sessionId = (Integer) session.getAttribute("id");
        Role sessionRole = (Role) session.getAttribute("role");
        Map<Integer, Account> messagesAccounts = new HashMap<>();
        Account account = accountService.get(id);
        Account actionAccount = actionId == 0 ? null : accountService.get(actionId);
        Role role = accountService.getRole(id);
        Status status = relationshipService.getStatus(sessionId, id);
        Status pendingStatus = relationshipService.getPendingRequestToMe(id, sessionId);
        List<Message> messages = messageService.getAllByTypeAndAssignId(MessageType.ACCOUNT_WALL, account.getId());
        for (Message item : messages) {
            int accountId = item.getUserCreatorId();
            messagesAccounts.put(accountId, accountService.get(accountId));
        }
        req.setAttribute("id", id);
        req.setAttribute("account", account);
        req.setAttribute("actionAccount", actionAccount);
        req.setAttribute("role", role);
        req.setAttribute("sessionRole", sessionRole);
        req.setAttribute("status", status);
        req.setAttribute("pendingStatus", pendingStatus);
        req.setAttribute("infoMessage", infoMessage);
        req.setAttribute("messages", messages);
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("messagesAccounts", messagesAccounts);
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }
}
