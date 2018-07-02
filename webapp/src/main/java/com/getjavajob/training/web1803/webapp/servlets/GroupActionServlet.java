package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
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
        String infoMessage = "groupActionFalse";
        infoMessage = doAction(action, actionId, groupId, service, infoMessage);
        response.sendRedirect("GroupViewServlet?id=" + groupId + "&infoMessage=" + infoMessage + "&actionId=" + actionId);
    }

    private String doAction(String action, int actionId, int groupId, GroupService service, String infoMessage) {
        switch (action) {
            case "acceptMember":
                if (service.setStatusMemberInGroup(groupId, actionId, GroupStatus.ACCEPTED)) {
                    infoMessage = "acceptTrue";
                }
                break;
            case "declineMember":
                if (service.setStatusMemberInGroup(groupId, actionId, GroupStatus.DECLINE)) {
                    infoMessage = "declineTrue";
                }
                break;
            case "removeMember":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "removeTrue";
                }
                break;
            case "toUser":
                if (service.setRoleMemberInGroup(groupId, actionId, GroupRole.USER)) {
                    infoMessage = "toUserTrue";
                }
                break;
            case "toAdmin":
                if (service.setRoleMemberInGroup(groupId, actionId, GroupRole.ADMIN)) {
                    infoMessage = "toAdminTrue";
                }
                break;
            case "addPending":
                if (service.addPendingMemberToGroup(groupId, actionId)) {
                    infoMessage = "addPendingTrue";
                }
                break;
            case "removeRequest":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "removePendingTrue";
                }
                break;
            case "leaveGroup":
                if (service.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "leaveGroupTrue";
                }
                break;
            default:
                throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
        }
        return infoMessage;
    }
}