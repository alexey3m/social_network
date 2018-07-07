package com.getjavajob.training.web1803.webapp.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FriendsServlet extends ContextHttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int actionId = Integer.valueOf(request.getParameter("actionId"));
        HttpSession session = request.getSession();
        int currentId = (Integer) session.getAttribute("id");
        String infoMessage = "friendsFalse";
        switch (action) {
            case "add":
                if (relationshipService.addQueryFriend(currentId, actionId)) {
                    infoMessage = "friendsAddQueryTrue";
                }
                break;
            case "accept":
                if (relationshipService.acceptFriend(currentId, actionId)) {
                    infoMessage = "friendsAcceptTrue";
                }
                break;
            case "decline":
                if (relationshipService.declineFriend(currentId, actionId)) {
                    infoMessage = "friendsDeclineTrue";
                }
                break;
            case "remove":
                if (relationshipService.removeFriend(currentId, actionId)) {
                    infoMessage = "friendsRemoveTrue";
                }
                break;
            case "removeRequest":
                if (relationshipService.removeFriend(currentId, actionId)) {
                    infoMessage = "removeRequestTrue";
                }
                break;
            default:
                throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
        }
        if (request.getServletPath().equals("/account.jsp")) {
            response.sendRedirect("AccountViewServlet?id=" + currentId + "&infoMessage=" + infoMessage + "&actionId=" + actionId);
        } else {
            response.sendRedirect("FriendsViewServlet?infoMessage=" + infoMessage + "&actionId=" + actionId);
        }
    }
}