package com.ofn.dao.controller;

import com.ofn.service.BlogService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class ContentController {

    private BlogService service;

    @Inject
    public ContentController(BlogService service){
        this.service = service;
    }


    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String welcomeMap(Model model) {
        Map<Integer, String> pageLinks = service.getPageLinks();
        model.addAttribute("pageLinks", pageLinks);
        return "index";
    }


    @RequestMapping(value="/returnRequest",method= RequestMethod.POST)
    public String returnRequest(HttpServletRequest request, Map<String, Object> model) {

        String userInput = request.getParameter("userInput");
        model.put("userInput", userInput);

        return "index";
    }
    @RequestMapping(value = "/createpost", method = RequestMethod.GET)
    public String showTest() {
              return "createpost";
    }
    
     @RequestMapping(value = "/createstaticpage", method = RequestMethod.GET)
    public String createStatic() {
        return "createstaticpage";
    }
    

}




