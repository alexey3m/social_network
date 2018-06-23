package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class CreateGroupServlet extends HttpServlet {
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
        GroupService service = new GroupService();
        try {
            boolean result = service.create(name, photo, photoFileName, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), info, idCreator);
            if (!result) {
                response.sendRedirect("createGroup.jsp?message=smFalse");
            } else {
                response.sendRedirect("group.jsp?id=" + service.getId(name) + "&message=reg");
            }
        } catch (DaoNameException e) {
            response.sendRedirect("createGroup.jsp?message=nameFalse&name=" + name);
        }
    }
}