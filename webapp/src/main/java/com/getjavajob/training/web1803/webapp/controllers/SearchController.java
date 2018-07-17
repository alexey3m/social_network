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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
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
        String temp = searchString.replaceAll("\\s+", " ").trim();
        List<Account> findAccounts = accountService.searchByString(temp);
        List<Group> findGroups = groupService.searchByString(temp);
        ModelAndView modelAndView = new ModelAndView("/jsp/search-result.jsp");
        modelAndView.addObject("searchString", temp);
        modelAndView.addObject("findAccounts", findAccounts);
        modelAndView.addObject("findGroups", findGroups);
        return modelAndView;
    }

    @RequestMapping(value = "/commonSearchFilter")
    @ResponseBody
    public List<String> commonSearchFilter(@RequestParam("filter") String filter) {

        List<Account> accounts = accountService.searchByString(filter);
        List<Group> groups = groupService.searchByString(filter);
        List<String> result = new ArrayList<>();
        for (Account account : accounts) {
            result.add(account.getFirstName() + " " + account.getMiddleName() + " " + account.getLastName());
        }
        for (Group group : groups) {
            result.add(group.getName());
        }
        Collections.sort(result);
        return result;
    }

    @RequestMapping(value = "/accountFilter")
    @ResponseBody
    public List<Account> accountFilter(@RequestParam("filter") String filter) {
        return accountService.searchByString(filter);
    }

    @RequestMapping(value = "/groupFilter")
    @ResponseBody
    public List<Group> groupFilter(@RequestParam("filter") String filter) {
        return groupService.searchByString(filter);
    }
}
