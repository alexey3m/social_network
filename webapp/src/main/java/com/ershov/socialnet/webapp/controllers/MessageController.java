package com.ershov.socialnet.webapp.controllers;

import com.ershov.socialnet.common.Account;
import com.ershov.socialnet.common.Message;
import com.ershov.socialnet.common.enums.MessageType;
import com.ershov.socialnet.service.AccountService;
import com.ershov.socialnet.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public MessageController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @RequestMapping("/viewAccountMess")
    public ModelAndView viewAccountMessages(@RequestParam(required = false, name = "id") Integer id,
                                            @RequestParam(required = false, name = "assignId") Integer assignId,
                                            HttpSession session) {
        logger.info("In viewAccountMessages method");
        int sessionId = (Integer) session.getAttribute("id");
        if (assignId == null) {
            assignId = id != null ? id : 0;
        }
        Account newMessageAccount = new Account();
        List<Account> contacts = new ArrayList<>();
        Map<Integer, Integer> allMessagesByAssignId;
        List<Message> allMessages = new ArrayList<>();
        Map<Integer, Account> allAccountsMessages = new HashMap<>();
        for (int accountId : messageService.getAllAccountIdDialog(sessionId)) {
            contacts.add(accountService.get(accountId));
        }
        if (assignId != 0) {
            newMessageAccount = accountService.get(assignId);
            allMessagesByAssignId = messageService.getAllByCurrentIdAssignId(sessionId, assignId);
            for (int messageId : allMessagesByAssignId.keySet()) {
                allMessages.add(messageService.get(messageId));
            }
            for (int accountId : allMessagesByAssignId.values()) {
                allAccountsMessages.put(accountId, accountService.get(accountId));
            }
        }
        ModelAndView modelAndView = new ModelAndView("accountMess");
        modelAndView.addObject("sessionId", sessionId);
        modelAndView.addObject("assignId", assignId);
        modelAndView.addObject("newMessageAccount", newMessageAccount);
        modelAndView.addObject("contacts", contacts);
        modelAndView.addObject("allMessages", allMessages);
        modelAndView.addObject("allAccountsMessages", allAccountsMessages);
        return modelAndView;
    }

    @RequestMapping("/viewMessagePhoto")
    public String viewMessagePhoto(byte[] photo) {
        logger.info("In viewMessagePhoto method");
        byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
        try {
            return new String(encodedPhotoBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Encode bytes to UTF-8 end with error! ", e);
            return null;
        }
    }

    @RequestMapping("/getMessagePhoto")
    public void getMessagePhoto(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
        logger.info("In getMessagePhoto method");
        byte[] photo = messageService.getPhoto(id);
        if (photo == null) {
            response.sendError(SC_NOT_FOUND);
        } else {
            response.setContentType("image/jpg");
            OutputStream os = response.getOutputStream();
            os.write(photo);
            os.flush();
            os.close();
        }
    }

    @RequestMapping(value = "/messageAction", method = RequestMethod.POST)
    public String messageAction(@RequestParam("assignId") int assignId,
                                @RequestParam("status") String inputStatus,
                                @RequestParam("action") String action,
                                @RequestParam(required = false, name = "inputNewMessage") String text,
                                @RequestParam(required = false, name = "messageId") Integer messageId,
                                @RequestParam(required = false, name = "uploadImage") MultipartFile file,
                                HttpSession session) {
        logger.info("In messageAction method");
        MessageType type = null;
        String location;
        switch (inputStatus) {
            case "accountWall":
                type = MessageType.ACCOUNT_WALL;
                location = "viewAccount";
                break;
            case "account":
                type = MessageType.ACCOUNT;
                location = "viewAccountMess";
                break;
            case "groupWall":
                type = MessageType.GROUP_WALL;
                location = "viewGroup";
                break;
            default:
                location = "";
                break;
        }
        if (action.equals("new")) {
            byte[] photo = null;
            if (!file.isEmpty()) {
                try {
                    photo = file.getBytes();
                } catch (IOException e) {
                    logger.error("Error in get Bytes method", e);
                }
            }
            int currentId = (Integer) session.getAttribute("id");
            messageService.create(new Message(assignId, type, photo, text, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), currentId));
        } else if (action.equals("remove")) {
            messageService.remove(messageId);
        }
        return "redirect:" + location + "?id=" + assignId;
    }

    @RequestMapping(value = "/goChat")
    public ModelAndView goChat(HttpSession session) {
        int sessionId = (Integer) session.getAttribute("id");
        ModelAndView modelAndView = new ModelAndView("chat");
        Account currentAccount = accountService.get(sessionId);

        modelAndView.addObject("username", currentAccount.getFirstName() + " " + currentAccount.getLastName());
        modelAndView.addObject("id", sessionId);
        return modelAndView;
    }
}