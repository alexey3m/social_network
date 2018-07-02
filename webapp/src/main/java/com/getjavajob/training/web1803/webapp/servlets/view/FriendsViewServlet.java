package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.RelationshipService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FriendsViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        int id = (Integer) session.getAttribute("id");
        String infoMessage = req.getParameter("infoMessage");
        String actionIdString = req.getParameter("actionId");
        int actionId = actionIdString != null ? Integer.valueOf(actionIdString) : 0;

        RelationshipService relationshipService = new RelationshipService();
        AccountService accountService = new AccountService();

        Account actionAccount = actionId == 0 ? null : accountService.get(actionId);
        List<Account> myRequest = relationshipService.getFriendRequestsFromId(id);
        List<Account> pendingRequest = relationshipService.getPendingRequestsToId(id);
        List<Account> friends = relationshipService.getAcceptedFriendsList(id);
        req.setAttribute("id", id);
        req.setAttribute("infoMessage", infoMessage);
        req.setAttribute("actionId", actionId);
        req.setAttribute("actionAccount", actionAccount);
        req.setAttribute("myRequest", myRequest);
        req.setAttribute("pendingRequest", pendingRequest);
        req.setAttribute("friends", friends);
        req.getRequestDispatcher("friends.jsp").forward(req, resp);
    }
}
