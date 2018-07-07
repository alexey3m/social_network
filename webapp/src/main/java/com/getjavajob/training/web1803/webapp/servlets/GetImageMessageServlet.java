package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GetImageMessageServlet extends ContextHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Message message = messageService.get(id);
        byte[] photo = message != null ? message.getPhoto() : null;
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
}
