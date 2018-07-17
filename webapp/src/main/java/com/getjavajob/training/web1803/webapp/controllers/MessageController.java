package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.MessageService;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Controller
public class MessageController {

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
        ModelAndView modelAndView = new ModelAndView("/jsp/accountMess.jsp");
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
        byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);

        try {
            return new String(encodedPhotoBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/getMessagePhoto")
    public void getMessagePhoto(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
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
                                @RequestParam("type") String inputType,
                                @RequestParam("action") String action,
                                @RequestParam(required = false, name = "inputNewMessage") String text,
                                @RequestParam(required = false, name = "messageId") Integer messageId,
                                @RequestParam(required = false, name = "uploadImage") MultipartFile filePart,
                                HttpSession session) {
        int groupId = 0;
        int accountId = 0;
        MessageType type = null;
        String location;
        switch (inputType) {
            case "accountWall":
                type = MessageType.ACCOUNT_WALL;
                accountId = assignId;
                location = "viewAccount";
                break;
            case "account":
                type = MessageType.ACCOUNT;
                accountId = assignId;
                location = "viewAccountMess";
                break;
            case "groupWall":
                type = MessageType.GROUP_WALL;
                groupId = assignId;
                location = "viewGroup";
                break;
            default:
                location = "";
                break;
        }
        if (action.equals("new")) {
            InputStream photo = null;
            String photoFileName = null;
            if (!filePart.isEmpty()) {
                try {
                    photo = filePart.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                photoFileName = filePart.getOriginalFilename();
            }
            int currentId = (Integer) session.getAttribute("id");
            messageService.create(groupId, accountId, type, photo, photoFileName, text, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), currentId);
        } else if (action.equals("remove")) {
            messageService.remove(messageId);
        }
        return "redirect:" + location + "?id=" + assignId;
    }
}