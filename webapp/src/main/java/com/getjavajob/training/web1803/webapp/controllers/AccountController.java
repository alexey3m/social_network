package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.MessageService;
import com.getjavajob.training.web1803.service.RelationshipService;
import com.getjavajob.training.web1803.webapp.convertors.PhoneTypeEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@ImportResource({"classpath*:security.xml"})
public class AccountController {
    private static final String ACCOUNT = "account";
    private static final String UTF8_ENCODING = "UTF-8";
    private static final String ENCODED_PHOTO = "encodedPhoto";
    private static final String REDIRECT_TO_VIEW_ACCOUNT = "redirect:viewAccount?id=";
    private static final String MESSAGE_ENCODE = "Encode bytes to UTF-8 end with error! Exception: ";
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final int BUFFER_SIZE = 4096;

    private AccountService accountService;
    private RelationshipService relationshipService;
    private MessageService messageService;

    @Autowired
    public AccountController(AccountService accountService, RelationshipService relationshipService, MessageService messageService) {
        this.accountService = accountService;
        this.relationshipService = relationshipService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpSession session) {
        int sessionId = (Integer) session.getAttribute("id");
        return REDIRECT_TO_VIEW_ACCOUNT + sessionId;
    }

    @RequestMapping(value = "/errorAccess", method = RequestMethod.GET)
    public String errorAccessPage() {
        return "403";
    }

    @RequestMapping(value = "/viewAccount", method = RequestMethod.GET)
    public ModelAndView viewAccount(@RequestParam("id") int id,
                                    @RequestParam(required = false, name = "actionId") Integer actionId,
                                    @RequestParam(required = false, name = "infoMessage") String infoMessage,
                                    HttpSession session) {
        logger.info("In viewAccount method, id = " + id);
        int sessionId = (Integer) session.getAttribute("id");
        Role sessionRole = (Role) session.getAttribute("role");
        Map<Integer, Account> messagesAccounts = new HashMap<>();
        Account account = accountService.get(id);
        if (account == null) {
            logger.warn("account is null. Redirect to 404 page.");
            return new ModelAndView("redirect:page404");
        }
        Account actionAccount = actionId == null ? new Account() : accountService.get(actionId);
        Role role = accountService.getRole(id);
        Status status = relationshipService.getStatus(sessionId, id);
        Status pendingStatus = relationshipService.getPendingRequestToMe(id, sessionId);
        List<Message> messages = messageService.getAllByTypeAndAssignId(MessageType.ACCOUNT_WALL, account.getId());
        for (Message item : messages) {
            int accountId = item.getUserCreatorId();
            messagesAccounts.put(accountId, accountService.get(accountId));
        }
        String encodedPhoto = "";
        byte[] photo = account.getPhoto();
        if (photo != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(account.getPhoto());
            try {
                encodedPhoto = new String(encodedPhotoBytes, UTF8_ENCODING);
            } catch (UnsupportedEncodingException e) {
                logger.error(MESSAGE_ENCODE, e);
            }
        }
        ModelAndView modelAndView = new ModelAndView(ACCOUNT);
        modelAndView.addObject("id", id);
        modelAndView.addObject(ACCOUNT, account);
        modelAndView.addObject("actionAccount", actionAccount);
        modelAndView.addObject("role", role);
        modelAndView.addObject("sessionRole", sessionRole);
        modelAndView.addObject("status", status);
        modelAndView.addObject("pendingStatus", pendingStatus);
        modelAndView.addObject("infoMessage", infoMessage);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("messagesAccounts", messagesAccounts);
        modelAndView.addObject("sessionId", sessionId);
        modelAndView.addObject(ENCODED_PHOTO, encodedPhoto);
        return modelAndView;
    }

    @RequestMapping(value = "/regPage", method = RequestMethod.GET)
    public ModelAndView viewRegPage() {
        logger.info("In viewRegPage method");
        return new ModelAndView("reg", ACCOUNT, new Account());
    }

    @RequestMapping(value = "/searchPage", method = RequestMethod.GET)
    public String searchPage() {
        logger.info("In searchPage method");
        return "search";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String regAccount(@ModelAttribute Account account,
                             @RequestParam(required = false, name = "uploadPhoto") MultipartFile file, BindingResult result) {
        logger.info("In regAccount method");
        byte[] currentAccountPhoto = null;
        if (!file.isEmpty()) {
            try {
                currentAccountPhoto = file.getBytes();
            } catch (IOException e) {
                logger.error("Get bytes from file end with error! Exception: ", e);
            }
        }
        account.setPhoto(currentAccountPhoto);
        account.setRegDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        account.setRole(Role.ROLE_USER);
        try {
            accountService.create(account);
            return "redirect:/loginPage?&infoMessage=reg";
        } catch (DaoNameException e) {
            return "redirect:/regPage?infoMessage=emailFalse";
        }
    }

    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public String updateAccount(@ModelAttribute Account account,
                                @RequestParam(required = false, name = "photoUpdate") MultipartFile file,
                                HttpSession session, BindingResult result) {
        logger.info("In updateAccount method");
        int id = account.getId();
        byte[] currentAccountPhoto = accountService.getPhoto(id);
        if (!file.isEmpty()) {
            try {
                currentAccountPhoto = file.getBytes();
            } catch (IOException e) {
                logger.error("Get bytes from file end with error! Exception: ", e);
            }
        }
        account.setPhoto(currentAccountPhoto);
        boolean resultUpdate = accountService.update(account);
        if (resultUpdate) {
            session.setAttribute("userName", account.getFirstName() + " " + account.getLastName());
            return REDIRECT_TO_VIEW_ACCOUNT + id + "&infoMessage=updateTrue";
        } else {
            return REDIRECT_TO_VIEW_ACCOUNT + id + "&infoMessage=updateFalse";
        }
    }

    @RequestMapping(value = "/updateAccountPage", method = RequestMethod.GET)
    public ModelAndView updateAccountPage(@RequestParam("id") int id,
                                          HttpSession session) {
        logger.info("In updateAccountPage method");
        int sessionId = (Integer) session.getAttribute("id");
        Role sessionRole = (Role) session.getAttribute("role");
        if (id != sessionId && sessionRole != Role.ROLE_ADMIN) {
            return new ModelAndView("/");
        } else {
            Account account = accountService.get(id);
            String encodedPhoto = "";
            byte[] photo = account.getPhoto();
            if (photo != null) {
                byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
                try {
                    encodedPhoto = new String(encodedPhotoBytes, UTF8_ENCODING);
                } catch (UnsupportedEncodingException e) {
                    logger.error(MESSAGE_ENCODE, e);
                }
            }
            ModelAndView modelAndView = new ModelAndView("update-account");
            modelAndView.addObject(ACCOUNT, account);
            modelAndView.addObject(ENCODED_PHOTO, encodedPhoto);
            return modelAndView;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PhoneType.class, new PhoneTypeEditor());
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public String updateRole(@RequestParam("action") String action,
                             @RequestParam("actionId") int actionId) {
        logger.info("In updateRole method");
        String message = "updateRoleFalse";
        if (action.equals("toUser")) {
            accountService.updateRole(actionId, Role.ROLE_USER);
            message = "updateRoleUser";
        } else if (action.equals("toAdmin")) {
            accountService.updateRole(actionId, Role.ROLE_ADMIN);
            message = "updateRoleAdmin";
        }
        return REDIRECT_TO_VIEW_ACCOUNT + actionId + "&infoMessage=" + message;
    }

    @RequestMapping(value = "/page404")
    public String updateRole() {
        return "404";
    }

    @RequestMapping(value = "/accountToXml")
    public void accountToXml(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response) {
        logger.info("In accountToXml method");
        Account account = accountService.get(id);
        File file = new File("account.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.marshal(account, file);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: ", e);
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentType("application/xml");
            response.setContentLength((int) file.length());
            byte[] buffer = new byte[BUFFER_SIZE];
            OutputStream outStream = response.getOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.close();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            logger.error("IOException: ", e);
        }
    }

    @RequestMapping(value = "/updateAccountPageFromXml", method = RequestMethod.POST)
    public ModelAndView updateAccountPageFromXml(@RequestParam("uploadXml") MultipartFile file,
                                                 HttpSession session) {
        logger.info("In updateAccountPage method");
        Account account = new Account();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File fileToUpdate = new File(file.getOriginalFilename());
            file.transferTo(fileToUpdate);
            account = (Account) unmarshaller.unmarshal(fileToUpdate);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: ", e);
        } catch (IOException e) {
            logger.error("Error with transfer to file. Exception: ", e);
        }
        String encodedPhoto = "";
        byte[] photo = accountService.getPhoto(account.getId());
        if (photo != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
            try {
                encodedPhoto = new String(encodedPhotoBytes, UTF8_ENCODING);
            } catch (UnsupportedEncodingException e) {
                logger.error(MESSAGE_ENCODE, e);
            }
        }
        account.setRole(accountService.getRole(account.getId()));
        ModelAndView modelAndView = new ModelAndView("update-account");
        modelAndView.addObject(ACCOUNT, account);
        modelAndView.addObject(ENCODED_PHOTO, encodedPhoto);
        return modelAndView;
    }

    @RequestMapping(value = "/removeAccount")
    public String removeAccount(@RequestParam("actionId") int actionId, HttpSession session) {
        logger.info("In removeAccount method");
        int sessionId = (Integer) session.getAttribute("id");
        accountService.remove(actionId);
        return REDIRECT_TO_VIEW_ACCOUNT + sessionId + "&infoMessage=remove";
    }
}