package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountMessViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService accountService = new AccountService();
        MessageService messageService = new MessageService();
        HttpSession session = req.getSession(false);
        int sessionId = (Integer) session.getAttribute("id");
        String assignIdString = req.getParameter("assignId");
        String idString = req.getParameter("id");
        int assignId = assignIdString != null ? Integer.valueOf(assignIdString) : idString != null ? Integer.valueOf(idString) : 0;
        Account newMessageAccount = null;
        List<Account> contacts = new ArrayList<>();
        Map<Integer, Integer> allMessagesByAssignId;
        List<Message> allMessages = new ArrayList<>();
        Map<Integer, Account> allAccountsMessages = new HashMap<>();
        for (int id : messageService.getAllAccountIdDialog(sessionId)) {
            contacts.add(accountService.get(id));
        }
        if (assignId != 0) {
            newMessageAccount = accountService.get(assignId);
            allMessagesByAssignId = messageService.getAllByCurrentIdAssignId(sessionId, assignId);
            for (int id : allMessagesByAssignId.keySet()) {
                allMessages.add(messageService.get(id));
            }
            for (int id : allMessagesByAssignId.values()) {
                allAccountsMessages.put(id, accountService.get(id));
            }
        }
        accountService.closeService();
        messageService.closeService();
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("assignId", assignId);
        req.setAttribute("newMessageAccount", newMessageAccount);
        req.setAttribute("contacts", contacts);
        req.setAttribute("allMessages", allMessages);
        req.setAttribute("allAccountsMessages", allAccountsMessages);
        req.getRequestDispatcher("accountMess.jsp").forward(req, resp);
    }
}
