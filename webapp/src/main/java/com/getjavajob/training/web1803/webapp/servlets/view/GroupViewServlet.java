package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.webapp.servlets.ContextHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupViewServlet extends ContextHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int groupId = Integer.valueOf(req.getParameter("id"));
        String infoMessage = req.getParameter("infoMessage");
        String actionIdString = req.getParameter("actionId");
        int actionId = actionIdString != null ? Integer.valueOf(actionIdString) : 0;
        HttpSession session = req.getSession(true);
        int sessionId = (Integer) session.getAttribute("id");
        List<Account> pendingMembers = new ArrayList<>();
        List<Account> acceptedMembers = new ArrayList<>();
        Map<Integer, GroupRole> acceptedMembersRole = new HashMap<>();
        Map<Integer, Account> messagesAccounts = new HashMap<>();
        Account actionAccount = actionId == 0 ? null : accountService.get(actionId);
        Group group = groupService.get(groupId);
        GroupRole role = groupService.getRoleMemberInGroup(groupId, sessionId);
        Role globalRole = accountService.getRole(sessionId);
        GroupStatus status = groupService.getStatusMemberInGroup(groupId, sessionId);
        Account accountCreator = accountService.get(group.getUserCreatorId());
        for (int id : group.getPendingMembersId()) {
            pendingMembers.add(accountService.get(id));
        }
        for (int id : group.getAcceptedMembersId()) {
            acceptedMembers.add(accountService.get(id));
            acceptedMembersRole.put(id, groupService.getRoleMemberInGroup(groupId, id));
        }
        List<Message> messages = messageService.getAllByTypeAndAssignId(MessageType.GROUP_WALL, group.getId());
        for (Message item : messages) {
            int accountId = item.getUserCreatorId();
            messagesAccounts.put(accountId, accountService.get(accountId));
        }
        req.setAttribute("groupId", groupId);
        req.setAttribute("infoMessage", infoMessage);
        req.setAttribute("actionId", actionId);
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("actionAccount", actionAccount);
        req.setAttribute("accountCreator", accountCreator);
        req.setAttribute("group", group);
        req.setAttribute("role", role);
        req.setAttribute("globalRole", globalRole);
        req.setAttribute("status", status);
        req.setAttribute("pendingMembers", pendingMembers);
        req.setAttribute("acceptedMembers", acceptedMembers);
        req.setAttribute("acceptedMembersRole", acceptedMembersRole);
        req.setAttribute("messages", messages);
        req.setAttribute("messagesAccounts", messagesAccounts);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }
}
