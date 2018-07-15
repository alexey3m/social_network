package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.MessageService;
import com.getjavajob.training.web1803.service.RelationshipService;
import com.getjavajob.training.web1803.webapp.PhoneTypeEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AccountController {

    private AccountService accountService;
    private RelationshipService relationshipService;
    private MessageService messageService;

    @Autowired
    public AccountController(AccountService accountService, RelationshipService relationshipService, MessageService messageService) {
        this.accountService = accountService;
        this.relationshipService = relationshipService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/viewAccount", method = RequestMethod.GET)
    public ModelAndView viewAccount(@RequestParam("id") int id,
                                    @RequestParam(required = false, name = "actionId") Integer actionId,
                                    @RequestParam(required = false, name = "infoMessage") String infoMessage,
                                    HttpSession session) {
        int sessionId = (Integer) session.getAttribute("id");
        Role sessionRole = (Role) session.getAttribute("role");
        Map<Integer, Account> messagesAccounts = new HashMap<>();
        Account account = accountService.get(id);
        if (account == null) {
            return new ModelAndView("/");
        }
        Account actionAccount = actionId == null ? new Account() : accountService.get(actionId);
        Role role = accountService.getRole(id);
        Status status = relationshipService.getStatus(sessionId, id);
        Status pendingStatus = relationshipService.getPendingRequestToMe(id, sessionId);
        List<Message> messages = messageService.getAllByTypeAndAssignId(MessageType.ACCOUNT_WALL, account.getId());
        for (Message item : messages) {
            int accountId = item.getUserCreatorId();
            messagesAccounts.put(accountId, accountService.get(accountId));
        }
        String encodedPhoto = "";
        byte[] photo = account.getPhoto();
        if (photo != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(account.getPhoto());
            try {
                encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        ModelAndView modelAndView = new ModelAndView("/jsp/account.jsp");
        modelAndView.addObject("id", id);
        modelAndView.addObject("account", account);
        modelAndView.addObject("actionAccount", actionAccount);
        modelAndView.addObject("role", role);
        modelAndView.addObject("sessionRole", sessionRole);
        modelAndView.addObject("status", status);
        modelAndView.addObject("pendingStatus", pendingStatus);
        modelAndView.addObject("infoMessage", infoMessage);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("messagesAccounts", messagesAccounts);
        modelAndView.addObject("sessionId", sessionId);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        return modelAndView;
    }

    @RequestMapping(value = "/regPage", method = RequestMethod.GET)
    public ModelAndView viewRegPage() {
        return new ModelAndView("/jsp/reg.jsp", "account", new Account());
    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String regAccount(@ModelAttribute Account account,
                             @RequestParam(required = false, name = "uploadPhoto") MultipartFile file, BindingResult result) {
        byte[] currentAccountPhoto = new byte[0];
        if (!file.isEmpty()) {
            try {
                currentAccountPhoto = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        account.setPhoto(currentAccountPhoto);
        account.setRegDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        account.setRole(Role.USER);
        try {
            accountService.create(account);
            return "redirect:/jsp/login.jsp?&infoMessage=reg";
        } catch (DaoNameException e) {
            return "redirect:/regPage?infoMessage=emailFalse";
        }
    }

    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public String updateAccount(@ModelAttribute Account account,
                                @RequestParam(required = false, name = "photoUpdate") MultipartFile file,
                                HttpSession session, BindingResult result) {
        byte[] currentAccountPhoto = accountService.getPhoto(account.getId());
        if (!file.isEmpty()) {
            try {
                currentAccountPhoto = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        account.setPhoto(currentAccountPhoto);
        boolean resultUpdate = accountService.update(account);
        if (resultUpdate) {
            session.setAttribute("userName", account.getFirstName() + " " + account.getLastName());
            return "redirect:viewAccount?id=" + account.getId() + "&infoMessage=updateTrue";
        } else {
            return "redirect:viewAccount?id=" + account.getId() + "&infoMessage=updateFalse";
        }
    }

    @RequestMapping(value = "/updateAccountPage", method = RequestMethod.GET)
    public ModelAndView updateAccountPage(@RequestParam("id") int id,
                                          HttpSession session) {
        int sessionId = (Integer) session.getAttribute("id");
        Role sessionRole = (Role) session.getAttribute("role");
        if (id != sessionId && sessionRole != Role.ADMIN) {
            return new ModelAndView("/");
        } else {
            Account account = accountService.get(id);
            String encodedPhoto = "";
            byte[] photo = account.getPhoto();
            if (photo != null) {
                byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
                try {
                    encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            ModelAndView modelAndView = new ModelAndView("/jsp/update-account.jsp");
            modelAndView.addObject("account", account);
            modelAndView.addObject("encodedPhoto", encodedPhoto);
            modelAndView.addObject("phoneTypes", PhoneType.values());
            return modelAndView;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PhoneType.class, new PhoneTypeEditor());
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public String updateRole(@RequestParam("action") String action,
                             @RequestParam("actionId") int actionId) {
        String message = "updateRoleFalse";
        if (action.equals("toUser")) {
            accountService.updateRole(actionId, Role.USER);
            message = "updateRoleUser";
        } else if (action.equals("toAdmin")) {
            accountService.updateRole(actionId, Role.ADMIN);
            message = "updateRoleAdmin";
        }
        return "redirect:viewAccount?id=" + actionId + "&infoMessage=" + message;
    }
}