package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
public class UpdateGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(request.getParameter("inputId"));
        String name = request.getParameter("inputName");
        String info = request.getParameter("inputInfo");
        InputStream photo = null;
        String photoFileName = null;
        Part filePart = request.getPart("uploadPhoto");
        if (filePart != null) {
            photo = filePart.getInputStream();
            photoFileName = filePart.getName();
        }
        GroupService service = new GroupService();
        boolean result = service.update(name, photo, photoFileName, info);
        if (result) {
            response.sendRedirect("GroupViewServlet?id=" + id + "&infoMessage=updateTrue");
        } else {
            response.sendRedirect("GroupViewServlet?id=" + id + "&infoMessage=updateFalse");
        }
    }
}