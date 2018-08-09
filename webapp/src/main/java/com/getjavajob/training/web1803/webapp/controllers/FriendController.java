package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.RelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FriendController {
    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

    private AccountService accountService;
    private RelationshipService relationshipService;

    @Autowired
    public FriendController(AccountService accountService, RelationshipService relationshipService) {
        this.accountService = accountService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(value = "/viewFriends", method = RequestMethod.GET)
    public ModelAndView viewFriends(@RequestParam(required = false, name = "actionId") Integer actionId,
                                    @RequestParam(required = false, name = "infoMessage") String infoMessage,
                                    HttpSession session) {
        logger.info("In viewFriends method");
        int id = (Integer) session.getAttribute("id");
        actionId = actionId == null ? 0 : actionId;
        Account actionAccount = actionId == 0 ? new Account() : accountService.get(actionId);
        List<Account> myRequest = relationshipService.getFriendRequestsFromId(id);
        List<Account> pendingRequest = relationshipService.getPendingRequestsToId(id);
        List<Account> friends = relationshipService.getAcceptedFriendsList(id);
        ModelAndView modelAndView = new ModelAndView("friends");
        modelAndView.addObject("id", id);
        modelAndView.addObject("infoMessage", infoMessage);
        modelAndView.addObject("actionId", actionId);
        modelAndView.addObject("actionAccount", actionAccount);
        modelAndView.addObject("myRequest", myRequest);
        modelAndView.addObject("pendingRequest", pendingRequest);
        modelAndView.addObject("friends", friends);
        return modelAndView;
    }

    @RequestMapping(value = {"/friends", "/accountFriends"}, method = RequestMethod.POST)
    public String friends(@RequestParam("action") String action,
                          @RequestParam("actionId") Integer actionId,
                          HttpSession session,
                          HttpServletRequest request) {
        logger.info("In friends method");
        int currentId = (Integer) session.getAttribute("id");
        String infoMessage = "friendsFalse";
        switch (action) {
            case "add":
                if (relationshipService.addQueryFriend(currentId, actionId)) {
                    infoMessage = "friendsAddQueryTrue";
                }
                break;
            case "accept":
                if (relationshipService.acceptFriend(currentId, actionId)) {
                    infoMessage = "friendsAcceptTrue";
                }
                break;
            case "decline":
                if (relationshipService.declineFriend(currentId, actionId)) {
                    infoMessage = "friendsDeclineTrue";
                }
                break;
            case "remove":
                if (relationshipService.removeFriend(currentId, actionId)) {
                    infoMessage = "friendsRemoveTrue";
                }
                break;
            case "removeRequest":
                if (relationshipService.removeFriend(currentId, actionId)) {
                    infoMessage = "removeRequestTrue";
                }
                break;
            default:
                throw new UnsupportedOperationException("action: \"" + action + "\" do not recognized");
        }
        if (request.getServletPath().equals("/accountFriends")) {
            return "redirect:viewAccount?id=" + actionId + "&infoMessage=" + infoMessage + "&actionId=" + actionId;
        } else {
            return "redirect:viewFriends?infoMessage=" + infoMessage + "&actionId=" + actionId;
        }
    }
}