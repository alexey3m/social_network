package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {

    private AccountService accountService;
    private GroupService groupService;

    @Autowired
    public SearchController(AccountService accountService, GroupService groupService) {
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/viewSearch", method = RequestMethod.GET)
    public ModelAndView viewSearch(@RequestParam(required = false, name = "inputSearch") String searchString) {
        List<Account> findAccounts = accountService.searchByString(searchString);
        List<Group> findGroups = groupService.searchByString(searchString);
        ModelAndView modelAndView = new ModelAndView("/jsp/search-result.jsp");
        modelAndView.addObject("searchString", searchString);
        modelAndView.addObject("findAccounts", findAccounts);
        modelAndView.addObject("findGroups", findGroups);
        return modelAndView;
    }
}
