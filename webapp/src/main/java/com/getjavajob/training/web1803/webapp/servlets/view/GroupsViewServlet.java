package com.getjavajob.training.web1803.webapp.servlets.view;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.webapp.servlets.ContextHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GroupsViewServlet extends ContextHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        int sessionId = (Integer) session.getAttribute("id");
        List<Group> myGroups = groupService.getAllById(sessionId);
        List<Group> allGroups = groupService.getAll();
        req.setAttribute("myGroups", myGroups);
        req.setAttribute("allGroups", allGroups);
        req.getRequestDispatcher("groups.jsp").forward(req, resp);
    }
}
