package com.getjavajob.training.web1803.webapp.servlets;

import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
import com.getjavajob.training.web1803.service.MessageService;
import com.getjavajob.training.web1803.service.RelationshipService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;

public abstract class ContextHttpServlet extends HttpServlet {
    protected AccountService accountService;
    protected GroupService groupService;
    protected MessageService messageService;
    protected RelationshipService relationshipService;

    @Override
    public void init() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        accountService = applicationContext.getBean(AccountService.class);
        groupService = applicationContext.getBean(GroupService.class);
        messageService = applicationContext.getBean(MessageService.class);
        relationshipService = applicationContext.getBean(RelationshipService.class);
    }
}
