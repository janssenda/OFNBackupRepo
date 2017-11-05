package com.ofn.controller;

import com.ofn.model.BlogPost;
import com.ofn.model.Category;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        List<BlogPost> allBlogs = service.getPublishedPosts();
        model.addAttribute("allBlogs", allBlogs);
        return "index";
    }


    public static String formatLocalDateTime(LocalDateTime ldt, String pattern){
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String localDateTimeToString(LocalDateTime ldt){
        return ldt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:SS"));
    }

    @RequestMapping(value = "/createcontent", method = RequestMethod.GET)
    public String showTest(Model model) {
        List<Category> categories = service.getAllCategories();
        model.addAttribute("categories", categories);
        return "createcontent";
    }

    @RequestMapping(value = "/createstaticpage", method = RequestMethod.GET)
    public String createStatic() {
        return "createstaticpage";
    }
    

}




