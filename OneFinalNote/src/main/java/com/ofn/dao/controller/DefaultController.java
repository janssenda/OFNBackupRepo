package com.ofn.dao.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class DefaultController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomeMap() {
        return "index";
    }

    @RequestMapping(value="/returnRequest",method= RequestMethod.POST)
    public String returnRequest(HttpServletRequest request, Map<String, Object> model) {

        String userInput = request.getParameter("userInput");
        model.put("userInput", userInput);

        return "index";
    }

}




