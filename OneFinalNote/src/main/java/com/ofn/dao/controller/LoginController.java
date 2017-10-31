package com.ofn.dao.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "index";
    }

    @RequestMapping(value = "/createpost", method = RequestMethod.GET)
    public String showTest() {
        return "createpost";
    }
}