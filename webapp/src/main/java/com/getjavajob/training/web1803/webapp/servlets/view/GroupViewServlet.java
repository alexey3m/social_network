package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.*;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
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

public class GroupViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.valueOf(req.getParameter("id"));
        String infoMessage = req.getParameter("infoMessage");
        String actionIdString = req.getParameter("actionId");
        int actionId = actionIdString != null ? Integer.valueOf(actionIdString) : 0;
        HttpSession session = req.getSession(true);
        int sessionId = (Integer) session.getAttribute("id");

        AccountService accountService = new AccountService();
        GroupService groupService = new GroupService();
        MessageService messageService = new MessageService();

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
        accountService.closeService();
        groupService.closeService();
        messageService.closeService();

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
