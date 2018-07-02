package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
public class UpdateAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(request.getParameter("inputId"));
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        String firstName = request.getParameter("inputFirstName");
        String lastName = request.getParameter("inputLastName");
        String middleName = request.getParameter("inputMiddleName");
        String birthday = request.getParameter("inputBirthday").equals("") ? null : request.getParameter("inputBirthday");
        Map<String, PhoneType> phones = new HashMap<>();
        String phonePers = request.getParameter("inputPhonePers");
        if (!phonePers.equals("")) {
            phones.put(phonePers, PhoneType.HOME);
        }
        String phoneWork = request.getParameter("inputPhoneWork");
        if (!phoneWork.equals("")) {
            phones.put(phoneWork, PhoneType.WORK);
        }
        String phoneAdd = request.getParameter("inputPhoneAdd");
        if (!phoneAdd.equals("")) {
            phones.put(phoneAdd, PhoneType.ADDITIONAL);
        }
        String skype = request.getParameter("inputSkype");
        String icqString = request.getParameter("inputIcq");
        int icq = icqString.equals("") ? 0 : Integer.valueOf(icqString);
        InputStream photo = null;
        String photoFileName = null;
        Part filePart = request.getPart("uploadPhoto");
        if (filePart != null) {
            photo = filePart.getInputStream();
            photoFileName = filePart.getName();
        }
        AccountService accountService = new AccountService();
        boolean result = accountService.update(email, password, firstName, lastName, middleName, birthday, photo,
                photoFileName, skype, icq, phones);
        if (result) {
            response.sendRedirect("AccountViewServlet?id=" + id + "&infoMessage=updateTrue");
        } else {
            response.sendRedirect("AccountViewServlet?id=" + id + "&infoMessage=updateFalse");
        }
    }
}