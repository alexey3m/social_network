package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.GroupRole;
import com.getjavajob.training.web1803.common.GroupStatus;
import com.getjavajob.training.web1803.service.GroupService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int actionId = Integer.valueOf(request.getParameter("actionId"));
        int groupId = Integer.valueOf(request.getParameter("groupId"));
        GroupService service = new GroupService();
        String message = "groupActionFalse";
        switch (action) {
            case "acceptMember":
                if (service.setStatusMemberInGroup(groupId, actionId, GroupStatus.ACCEPTED)) {
                    message = "acceptTrue";
                    break;
                }
            case "declineMember":
                if (service.setStatusMemberInGroup(groupId, actionId, GroupStatus.DECLINE)) {
                    message = "declineTrue";
                    break;
                }
            case "removeMember":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    message = "removeTrue";
                    break;
                }
            case "toUser":
                if (service.setRoleMemberInGroup(groupId, actionId, GroupRole.USER)) {
                    message = "toUserTrue";
                    break;
                }
            case "toAdmin":
                if (service.setRoleMemberInGroup(groupId, actionId, GroupRole.ADMIN)) {
                    message = "toAdminTrue";
                    break;
                }
            case "addPending":
                if (service.addPendingMemberToGroup(groupId, actionId)) {
                    message = "addPendingTrue";
                    break;
                }
            case "removeRequest":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    message = "removePendingTrue";
                    break;
                }
            case "leaveGroup":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    message = "leaveGroupTrue";
                    break;
                }
        }
        response.sendRedirect("group.jsp?id=" + groupId + "&message=" + message + "&actionId=" + actionId);
    }
}