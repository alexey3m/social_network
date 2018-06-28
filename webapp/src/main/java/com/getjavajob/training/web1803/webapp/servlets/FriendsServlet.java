package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.service.RelationshipService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FriendsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int actionId = Integer.valueOf(request.getParameter("actionId"));
        HttpSession session = request.getSession();
        int currentId = (Integer) session.getAttribute("id");
        RelationshipService service = new RelationshipService();
        String infoMessage = "friendsFalse";
        try {
            switch (action) {
                case "add":
                    if (service.addQueryFriend(currentId, actionId)) {
                        infoMessage = "friendsAddQueryTrue";
                    }
                    break;
                case "accept":
                    if (service.acceptFriend(currentId, actionId)) {
                        infoMessage = "friendsAcceptTrue";
                    }
                    break;
                case "decline":
                    if (service.declineFriend(currentId, actionId)) {
                        infoMessage = "friendsDeclineTrue";
                    }
                    break;
                case "remove":
                    if (service.removeFriend(currentId, actionId)) {
                        infoMessage = "friendsRemoveTrue";
                    }
                    break;
                case "removeRequest":
                    if (service.removeFriend(currentId, actionId)) {
                        infoMessage = "removeRequestTrue";
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } finally {
            service.closeService();
        }
        if (request.getServletPath().equals("/account.jsp")) {
            response.sendRedirect("AccountViewServlet?id=" + currentId + "&infoMessage=" + infoMessage + "&actionId=" + actionId);
        } else {
            response.sendRedirect("FriendsViewServlet?infoMessage=" + infoMessage + "&actionId=" + actionId);
        }
    }
}