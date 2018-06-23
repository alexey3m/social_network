package com.getjavajob.training.web1803.webapp.servlets;

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
        String message = "friendsFalse";
        switch (action) {
            case "add":
                if (service.addQueryFriend(currentId, actionId)) {
                    message = "friendsAddQueryTrue";
                }
                break;
            case "accept":
                if (service.acceptFriend(currentId, actionId)) {
                    message = "friendsAcceptTrue";
                }
                break;
            case "decline":
                if (service.declineFriend(currentId, actionId)) {
                    message = "friendsDeclineTrue";
                }
                break;
            case "remove":
                if (service.removeFriend(currentId, actionId)) {
                    message = "friendsRemoveTrue";
                }
                break;
            case "removeRequest":
                if (service.removeFriend(currentId, actionId)) {
                    message = "removeRequestTrue";
                }
                break;
            default:
                throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
        }
        if (request.getServletPath().equals("/account.jsp")) {
            response.sendRedirect("account.jsp?id=" + currentId + "&message=" + message + "&actionId=" + actionId);
        } else {
            response.sendRedirect("friends.jsp?message=" + message + "&actionId=" + actionId);
        }
    }
}