package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
public class RegServlet extends ContextHttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        String firstName = request.getParameter("inputFirstName");
        String lastName = request.getParameter("inputLastName");
        String middleName = request.getParameter("inputMiddleName");
        String birthday = request.getParameter("inputBirthday").equals("") ? null : request.getParameter("inputBirthday");
        String icqString = request.getParameter("inputIcq");
        int icq = icqString.equals("") ? 0 : Integer.valueOf(icqString);
        String skype = request.getParameter("inputSkype");
        Map<String, PhoneType> phones = new HashMap<>();
        String[] phonesStr = request.getParameterValues("inputPhone");
        String[] phonesTypeStr = request.getParameterValues("phoneSel");
        for (int i = 0; i < phonesStr.length; i++) {
            phones.put(phonesStr[i], PhoneType.values()[Integer.parseInt(phonesTypeStr[i])]);
        }
        InputStream photo = null;
        String photoFileName = null;
        Part filePart = request.getPart("uploadPhoto");
        if (filePart != null) {
            photo = filePart.getInputStream();
            photoFileName = filePart.getName();
        }
        try {
            accountService.create(email, password, firstName, lastName, middleName, birthday, photo, photoFileName, skype,
                    icq, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), Role.USER, phones);
            response.sendRedirect("login.jsp?&infoMessage=reg");
        } catch (DaoNameException e) {
            response.sendRedirect("reg.jsp?infoMessage=emailFalse");
        }
    }
}