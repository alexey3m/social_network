package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
import com.getjavajob.training.web1803.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GroupController {
    private static final String GROUP = "group";
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private AccountService accountService;
    private GroupService groupService;
    private MessageService messageService;

    @Autowired
    public GroupController(AccountService accountService, GroupService groupService, MessageService messageService) {
        this.accountService = accountService;
        this.groupService = groupService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/viewGroup", method = RequestMethod.GET)
    public ModelAndView viewGroup(@RequestParam("id") int groupId,
                                  @RequestParam(required = false, name = "actionId") Integer actionId,
                                  @RequestParam(required = false, name = "infoMessage") String infoMessage,
                                  HttpSession session) {
        logger.info("In viewGroup method");
        actionId = actionId == null ? 0 : actionId;
        int sessionId = (Integer) session.getAttribute("id");
        List<Account> pendingMembers = new ArrayList<>();
        List<Account> acceptedMembers = new ArrayList<>();
        Map<Integer, GroupRole> acceptedMembersRole = new HashMap<>();
        Map<Integer, Account> messagesAccounts = new HashMap<>();
        Account actionAccount = actionId == 0 ? new Account() : accountService.get(actionId);
        Group group = groupService.get(groupId);
        if (group == null) {
            return new ModelAndView("redirect:page404");
        }
        GroupRole role = groupService.getRoleMemberInGroup(groupId, sessionId);
        Role globalRole = accountService.getRole(sessionId);
        GroupStatus status = groupService.getStatusMemberInGroup(groupId, sessionId);
        Account accountCreator = accountService.get(group.getUserCreatorId());
        for (AccountInGroup accountInGroup : group.getAccounts()) {
            int userMemberId = accountInGroup.getUserMemberId();
            if (accountInGroup.getStatus() == GroupStatus.PENDING) {
                pendingMembers.add(accountService.get(userMemberId));
            } else if (accountInGroup.getStatus() == GroupStatus.ACCEPTED) {
                acceptedMembers.add(accountService.get(userMemberId));
                acceptedMembersRole.put(userMemberId, groupService.getRoleMemberInGroup(groupId, userMemberId));
            }
        }
        List<Message> messages = messageService.getAllByTypeAndAssignId(MessageType.GROUP_WALL, group.getId());
        for (Message item : messages) {
            int accountId = item.getUserCreatorId();
            messagesAccounts.put(accountId, accountService.get(accountId));
        }
        String encodedPhoto = "";
        if (group.getPhoto() != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(group.getPhoto());
            try {
                encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Encode bytes to UTF-8 end with error! Exception: ", e);
            }
        }
        ModelAndView modelAndView = new ModelAndView("/jsp/group.jsp");
        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("infoMessage", infoMessage);
        modelAndView.addObject("actionId", actionId);
        modelAndView.addObject("sessionId", sessionId);
        modelAndView.addObject("actionAccount", actionAccount);
        modelAndView.addObject("accountCreator", accountCreator);
        modelAndView.addObject(GROUP, group);
        modelAndView.addObject("role", role);
        modelAndView.addObject("globalRole", globalRole);
        modelAndView.addObject("status", status);
        modelAndView.addObject("pendingMembers", pendingMembers);
        modelAndView.addObject("acceptedMembers", acceptedMembers);
        modelAndView.addObject("acceptedMembersRole", acceptedMembersRole);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("messagesAccounts", messagesAccounts);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        return modelAndView;
    }

    @RequestMapping(value = "/viewGroups", method = RequestMethod.GET)
    public ModelAndView viewGroups(HttpSession session) {
        logger.info("In viewGroups method");
        int sessionId = (Integer) session.getAttribute("id");
        List<Group> myGroups = groupService.getAllByUserId(sessionId);
        List<Group> allGroups = groupService.getAll();
        ModelAndView modelAndView = new ModelAndView("/jsp/groups.jsp");
        modelAndView.addObject("myGroups", myGroups);
        modelAndView.addObject("allGroups", allGroups);
        return modelAndView;
    }

    @RequestMapping(value = "/updateGroupPage", method = RequestMethod.GET)
    public ModelAndView updateGroupPage(@RequestParam("id") int id, HttpSession session) {
        logger.info("In updateGroupPage method");
        Group group = groupService.get(id);
        byte[] encodedPhotoBytes = Base64.getEncoder().encode(group.getPhoto());
        String encodedPhoto = "";
        try {
            encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Encode bytes to UTF-8 end with error! Exception: ", e);
        }
        ModelAndView modelAndView = new ModelAndView("/jsp/update-group.jsp");
        modelAndView.addObject(GROUP, group);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        return modelAndView;
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public String updateGroup(@ModelAttribute Group group,
                              @RequestParam(required = false, name = "uploadPhoto") MultipartFile file,
                              HttpSession session) {
        logger.info("In updateGroup method");
        Group currentGroup = groupService.get(group.getId());
        byte[] currentGroupPhoto = currentGroup.getPhoto();
        if (!file.isEmpty()) {
            try {
                currentGroupPhoto = file.getBytes();
            } catch (IOException e) {
                logger.error("Get bytes from file end with error! Exception: ", e);
            }
        }
        group.setPhoto(currentGroupPhoto);
        group.setUserCreatorId(currentGroup.getUserCreatorId());
        group.setCreateDate(currentGroup.getCreateDate());
        group.setAccounts(currentGroup.getAccounts());
        boolean result = groupService.update(group);
        String redirect = "redirect:/viewGroup?id=" + group.getId() + "&infoMessage=";
        return result ? redirect + "updateTrue" : redirect + "updateFalse";
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public String createGroup(@ModelAttribute Group group,
                              @RequestParam(required = false, name = "uploadPhoto") MultipartFile file,
                              HttpSession session) {
        logger.info("In createGroup method");
        String name = group.getName();
        byte[] currentGroupPhoto = new byte[0];
        if (!file.isEmpty()) {
            try {
                currentGroupPhoto = file.getBytes();
            } catch (IOException e) {
                logger.error("Get bytes from file end with error! Exception: ", e);
            }
        }
        group.setUserCreatorId((Integer) session.getAttribute("id"));
        group.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        group.setPhoto(currentGroupPhoto);
        List<AccountInGroup> accounts = new ArrayList<>();
        accounts.add(new AccountInGroup(group.getUserCreatorId(), GroupRole.ADMIN, GroupStatus.ACCEPTED));
        group.setAccounts(accounts);
        try {
            groupService.create(group);
            return "redirect:viewGroups";
        } catch (DaoNameException e) {
            return "redirect:/createGroupPage?infoMessage=nameFalse&name=" + name;
        }
    }

    @RequestMapping(value = "/createGroupPage", method = RequestMethod.GET)
    public ModelAndView createGroupPage() {
        logger.info("In createGroupPage method");
        return new ModelAndView("/jsp/create-group.jsp", GROUP, new Group());
    }


    @RequestMapping(value = "/groupAction", method = RequestMethod.POST)
    public String groupAction(@RequestParam("action") String action,
                              @RequestParam("actionId") Integer actionId,
                              @RequestParam("groupId") Integer groupId) {
        logger.info("In groupAction method");
        String infoMessage = "groupActionFalse";
        infoMessage = doAction(action, actionId, groupId, groupService, infoMessage);
        return "redirect:viewGroup?id=" + groupId + "&infoMessage=" + infoMessage + "&actionId=" + actionId;
    }

    private String doAction(String action, int actionId, int groupId, GroupService groupService, String infoMessage) {
        switch (action) {
            case "acceptMember":
                if (groupService.setStatusMemberInGroup(groupId, actionId, GroupStatus.ACCEPTED)) {
                    infoMessage = "acceptTrue";
                }
                break;
            case "declineMember":
                if (groupService.setStatusMemberInGroup(groupId, actionId, GroupStatus.DECLINE)) {
                    infoMessage = "declineTrue";
                }
                break;
            case "removeMember":
                if (groupService.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "removeTrue";
                }
                break;
            case "toUser":
                if (groupService.setRoleMemberInGroup(groupId, actionId, GroupRole.USER)) {
                    infoMessage = "toUserTrue";
                }
                break;
            case "toAdmin":
                if (groupService.setRoleMemberInGroup(groupId, actionId, GroupRole.ADMIN)) {
                    infoMessage = "toAdminTrue";
                }
                break;
            case "addPending":
                if (groupService.addPendingMemberToGroup(groupId, actionId)) {
                    infoMessage = "addPendingTrue";
                }
                break;
            case "removeRequest":
                if (groupService.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "removePendingTrue";
                }
                break;
            case "leaveGroup":
                if (groupService.removeMemberFromGroup(groupId, actionId)) {
                    infoMessage = "leaveGroupTrue";
                }
                break;
            default:
                throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
        }
        return infoMessage;
    }
}