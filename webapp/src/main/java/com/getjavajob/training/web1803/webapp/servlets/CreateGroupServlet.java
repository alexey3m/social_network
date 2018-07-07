package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class CreateGroupServlet extends ContextHttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("inputName");
        String info = request.getParameter("inputInfo");
        InputStream photo = null;
        String photoFileName = null;
        Part filePart = request.getPart("uploadPhoto");
        if (filePart != null) {
            photo = filePart.getInputStream();
            photoFileName = filePart.getName();
        }
        HttpSession session = request.getSession();
        int idCreator = (Integer) session.getAttribute("id");
        try {
            boolean result = groupService.create(name, photo, photoFileName, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), info, idCreator);
            if (!result) {
                response.sendRedirect("createGroup.jsp?infoMessage=smFalse");
            } else {
                response.sendRedirect("GroupViewServlet?id=" + groupService.getId(name) + "&infoMessage=regGroup");
            }
        } catch (DaoNameException e) {
            response.sendRedirect("createGroup.jsp?infoMessage=nameFalse&name=" + name);
        }
    }
}