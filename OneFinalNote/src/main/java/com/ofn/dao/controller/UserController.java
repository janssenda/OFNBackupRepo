package com.ofn.dao.controller;

import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import com.ofn.service.BlogService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private BlogService dao;
    private PasswordEncoder encoder;

    @Inject
    public UserController(BlogService dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    // This endpoint retrieves all users from the database and puts the
    // List of users on the model
    @RequestMapping(value = "/displayuserlist", method = RequestMethod.GET)
    public String displayUserList(Map<String, Object> model) {
        List users = dao.getAllUsers();
        model.put("users", users);
        return "displayuserlist";
    }

    // This endpoint just displays the Add User form
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String displayUserForm(Map<String, Object> model) {
        return "signup";
    }

    // This endpoint processes the form data and creates a new User
    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        User newUser = new User();

        String clearPw = req.getParameter("password");
        String hashPw = encoder.encode(clearPw);

        newUser.setUserName(req.getParameter("username"));
        newUser.setUserPW(hashPw);


        newUser.addAuthority("ROLE_USER");
        dao.addUser(newUser);
        return "redirect:displayuserlist";
    }
    // This endpoint deletes the specified User
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public String deletUser(@RequestParam("userid") int userId,
                            Map<String, Object> model) {
        dao.removeUser(userId);
        return "redirect:displayuserlist";
    }


}
