package com.getjavajob.training.web1803.webapp.controllers;

import com.getjavajob.training.web1803.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @RequestMapping(value = "/loginUser", method = {RequestMethod.POST, RequestMethod.GET})
//    public String login(@RequestParam(required = false, name = "cookie") Boolean useCookies,
//                        @RequestParam(required = false, name = "rememberMe") Boolean rememberMe,
//                        @RequestParam(required = false, name = "inputEmail") String email,
//                        @RequestParam(required = false, name = "inputPassword") String password,
//                        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//        logger.info("In login method");
//        if (useCookies != null) {
//            Cookie[] cookies = request.getCookies();
//            for (Cookie cookie : cookies) {
//                String cookieName = cookie.getName();
//                if (cookieName.equals(EMAIL)) {
//                    email = cookie.getValue();
//                }
//                if (cookieName.equals(PASSWORD)) {
//                    password = cookie.getValue();
//                }
//            }
//        }
//        try {
//            int id = accountService.loginAndGetId(email, password);
//            Account currentAccount = accountService.get(id);
//            session.setAttribute(EMAIL, email);
//            session.setAttribute("id", id);
//            session.setAttribute("userName", currentAccount.getFirstName() + " " + currentAccount.getLastName());
//            session.setAttribute("role", currentAccount.getRole());
//            if (rememberMe != null) {
//                Cookie cookieUsername = new Cookie(EMAIL, email);
//                Cookie cookiePassword = new Cookie(PASSWORD, password);
//                Cookie cookieId = new Cookie("id", String.valueOf(id));
//                response.addCookie(cookieUsername);
//                response.addCookie(cookiePassword);
//                response.addCookie(cookieId);
//            }
//            return "redirect:/viewAccount?id=" + id;
//        } catch (DaoNameException e) {
//            return "redirect:/jsp/login.jsp?infoMessage=alert";
//        }
//    }
//
//    @RequestMapping("/logoutUser")
//    public String logoutUser(HttpSession session, HttpServletResponse response) {
//        logger.info("In logoutUser method");
//        session.removeAttribute(EMAIL);
//        session.removeAttribute("id");
//        session.removeAttribute("userName");
//        session.removeAttribute("role");
//        session.invalidate();
//        Cookie email = new Cookie("email", null);
//        email.setMaxAge(0);
//        email.setPath("/");
//        Cookie cookiePassword = new Cookie(PASSWORD, null);
//        cookiePassword.setMaxAge(0);
//        cookiePassword.setPath("/");
//        Cookie cookieId = new Cookie("id", null);
//        cookieId.setMaxAge(0);
//        cookieId.setPath("/");
//        response.addCookie(email);
//        response.addCookie(cookiePassword);
//        response.addCookie(cookieId);
//        return "/jsp/login.jsp";
//    }
}