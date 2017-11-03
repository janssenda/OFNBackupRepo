package com.ofn.dao.controller;

import com.ofn.dao.impl.PersistenceException;
import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import com.ofn.service.BlogService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class AccountHandlerController {

    private BlogService dao;
    private PasswordEncoder encoder;

    @Inject
    public AccountHandlerController(BlogService dao, PasswordEncoder encoder) {
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
    public String addUser(HttpServletRequest req, Model model) {
        User newUser = new User();
        //model.addAttribute("create-user-error","");

        String userName = req.getParameter("username");
        String clearPw = req.getParameter("password");
        String clearPwCheck = req.getParameter("password-check");

        if (dao.searchUsers(null,userName).size() > 0){
            model.addAttribute("userexistserror","The username has been taken.");
            model.addAttribute("username", userName);
            model.addAttribute("userr",1);
            return "signup";
        }


        if (!clearPw.equals(clearPwCheck)){
            model.addAttribute("pwmismatcherror","Passwords do not match!");
            model.addAttribute("username", userName);
            model.addAttribute("pwerr",1);
            return "signup";
        }

        newUser.setUserName(userName);
        newUser.setUserPW(encoder.encode(clearPw));

        newUser.addAuthority("ROLE_USER");
        try {
            dao.addUser(newUser);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
    // This endpoint deletes the specified User
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public String deletUser(@RequestParam("userid") int userId,
                            Map<String, Object> model) {
        try {
            dao.removeUser(userId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:displayuserlist";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "index";
    }

    @RequestMapping(value = "/createpost", method = RequestMethod.GET)
    public String showTest() {
        return "createpost";
    }


}
