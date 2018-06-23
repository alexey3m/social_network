package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.MessageType;
import com.getjavajob.training.web1803.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class MessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int assignId = Integer.valueOf(request.getParameter("assignId"));
        String inputType = request.getParameter("type");
        String action = request.getParameter("action");
        MessageService service = new MessageService();
        int groupId = 0;
        int accountId = 0;
        MessageType type = null;
        String location = null;
        switch (inputType) {
            case "accountWall":
                type = MessageType.ACCOUNT_WALL;
                accountId = assignId;
                location = "account.jsp";
                break;
            case "account":
                type = MessageType.ACCOUNT;
                accountId = assignId;
                location = "accountMess.jsp";
                break;
            case "groupWall":
                type = MessageType.GROUP_WALL;
                groupId = assignId;
                location = "group.jsp";
                break;
        }
        switch (action) {
            case "new":
                String text = request.getParameter("inputNewMessage");
                InputStream photo = null;
                String photoFileName = null;
                Part filePart = request.getPart("uploadImage");
                if (filePart != null) {
                    photo = filePart.getInputStream();
                    photoFileName = filePart.getSubmittedFileName();
                }
                HttpSession session = request.getSession();
                int currentId = (Integer) session.getAttribute("id");
                service.create(groupId, accountId, type, photo, photoFileName, text, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), currentId);
                break;
            case "remove":
                int messageId = Integer.valueOf(request.getParameter("messageId"));
                service.remove(messageId);
                break;
        }
        response.sendRedirect(location + "?id=" + assignId);
    }
}
