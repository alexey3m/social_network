package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.service.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class GetPhotoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        AccountService service = new AccountService();
        Account currentAccount = service.get(id);
        byte[] photo = currentAccount.getPhoto();
        if (photo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setContentType("image/jpg");
            OutputStream os = response.getOutputStream();
            os.write(photo);
            os.flush();
            os.close();
        }
    }
}