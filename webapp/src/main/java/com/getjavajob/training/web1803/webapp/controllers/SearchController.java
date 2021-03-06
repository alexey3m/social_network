package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private AccountService accountService;
    private GroupService groupService;

    @Autowired
    public SearchController(AccountService accountService, GroupService groupService) {
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/viewSearch", method = RequestMethod.GET)
    public ModelAndView viewSearch(@RequestParam(required = false, name = "inputSearch") String searchString) {
        logger.info("In viewSearch method");
        String temp = searchString.replaceAll("\\s+", " ").trim();
        ModelAndView modelAndView = new ModelAndView("search-result");
        modelAndView.addObject("searchString", temp);
        return modelAndView;
    }

    @RequestMapping(value = "/commonSearchFilter")
    @ResponseBody
    public List<String> commonSearchFilter(@RequestParam("filter") String filter) {
        logger.info("In commonSearchFilter method");
        List<Account> accounts = accountService.searchByString(filter, 1);
        List<Group> groups = groupService.searchByString(filter, 0);
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
    public @ResponseBody
    List<Account> accountFilter(@RequestParam("filter") String filter, @RequestParam("page") int page) {
        logger.info("In accountFilter method");
        return accountService.searchByString(filter, page);
    }

    @RequestMapping(value = "/accountFilterCount")
    public @ResponseBody
    long searchAccountByStringCount(@RequestParam("filter") String filter) {
        logger.info("In searchAccountByStringCount method");
        return accountService.searchByStringCount(filter);
    }

    @RequestMapping(value = "/groupFilter")
    public @ResponseBody
    List<Group> groupFilter(@RequestParam("filter") String filter, @RequestParam("page") int page) {
        logger.info("In groupFilter method");
        return groupService.searchByString(filter, page - 1);
    }

    @RequestMapping(value = "/groupFilterCount")
    public @ResponseBody
    long searchGroupByStringCount(@RequestParam("filter") String filter) {
        logger.info("In searchAccountByStringCount method");
        return groupService.searchByStringCount(filter);
    }
}