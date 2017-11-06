package com.ofn.controller;

import com.ofn.dao.impl.PersistenceException;
import com.ofn.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ContentController {

    private BlogService service;

    @Inject
    public ContentController(BlogService service){
        this.service = service;
    }


    @RequestMapping(value = {"", "/", "/home", "/index"}, method = RequestMethod.GET)
    public String welcomeMap(Model model) {
        Map<Integer, String> pageLinks = service.getPageLinks();
        model.addAttribute("pageLinks", pageLinks);
        List<BlogPost> allBlogs = service.getAllPosts();
        model.addAttribute("allBlogs", allBlogs);
        return "index";
    }


    public static String formatLocalDateTime(LocalDateTime ldt, String pattern){
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    @RequestMapping(value = "/createcontent", method = RequestMethod.GET)
    public String createContent(Model model) {
        List<Category> categories = service.getAllCategories();
        model.addAttribute("categories", categories);
        return "createcontent";
    }

    @RequestMapping(value = "/editcontent", method = RequestMethod.GET)
    public String editContent(HttpServletRequest request, Model model) {
        List<Category> categories = service.getAllCategories();
        int catID;
        String title;
        LocalDateTime startDate;
        LocalDateTime endDate;
        boolean published;
        String body;

        try {
            int id = Integer.parseInt(request.getParameter("contentID"));
            String type = request.getParameter("contentType");

            if (type.trim().equals("blog")){
                BlogPost post =  service.getBlogPost(id);
                catID = post.getCategoryId();
                title = post.getTitle();
                startDate = post.getStartDate();
                endDate = post.getEndDate();
                published = post.isPublished();
                body = post.getBody();

                model.addAttribute("catID", catID);
                model.addAttribute("startDate", startDate);
                model.addAttribute("endDate", endDate);


            } else {
                Page page = service.getPageById(id);
                title = page.getTitle();
                published = page.isPublished();
                body = page.getBody();

            }

            model.addAttribute("categories", categories);
            model.addAttribute("title",title);
            model.addAttribute("body",body);
            model.addAttribute("published", published);
            model.addAttribute("contentID", id);


        } catch (Exception e){
            return "createcontent";
        }

        return "createcontent";
    }


    @RequestMapping(value = "/deleteBlogPost", method = RequestMethod.GET)
    public String deleteBlogPost(HttpServletRequest request){
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        try {
            service.removePost(blogId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String addContent(HttpServletRequest request){
        int contentID = 0;
        try {
            contentID = Integer.parseInt(request.getParameter("contentID"));
        } catch (Exception e){}

        String title = request.getParameter("newBlogPostTitle");
        String body = request.getParameter("newBlogPost");
        String tagString = request.getParameter("tagSelector");
        //System.out.println(request.getParameter("categorySelector"));
        int categoryID = Integer.parseInt(request.getParameter("categorySelector"));
        LocalDateTime start = LocalDateTime.parse(request.getParameter("startDateSelector"));
        LocalDateTime end = LocalDateTime.parse(request.getParameter("endDateSelector"));
        boolean isPublishing = Boolean.parseBoolean(request.getParameter("publishedSelector"));
        boolean isApprovalNeeded = Boolean.parseBoolean(request.getParameter("isApprovalNeeded"));
        String radioChecked = request.getParameter("typeRadio");
        String userLoggedIn = request.getParameter("userLoggedIn");
        User u = service.getUserByName(userLoggedIn);
        if(radioChecked.equals("blog")){
            BlogPost bp = new BlogPost();
            bp.setUserId(u.getUserId());
            bp.setTitle(title);
            bp.setBody(body);
            bp.setCategoryId(categoryID);
            bp.setUpdateTime(LocalDateTime.now());
            bp.setStartDate(start);
            bp.setEndDate(end);
            bp.setPublished(isPublishing);
            if(isApprovalNeeded){
                bp.setPublished(false);
            }
            else{
                if(bp.isPublished()){
                    bp.setStatus();
                }
            }
            bp.setCommentList(new ArrayList<>());
            bp.setTagList(new ArrayList<>());
            try {

                if (contentID > 0){
                    bp.setBlogPostId(contentID);
                    service.updatePost(bp);
                } else {
                    service.addPost(bp);
                }

            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
        else{
            Page p = new Page();
            p.setTitle(title);
            p.setBody(body);
            p.setUser(u);
            p.setUpdatedTime(LocalDateTime.now());
            p.setPublished(isPublishing);
            try {

                if (contentID > 0){
                    p.setPageId(contentID);
                    service.updatePage(p);
                } else {
                    service.addPage(p);
                }

            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
        return "redirect:index";
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addCommentToBlogPost(HttpServletRequest request){
        String commentBody = request.getParameter("commentBody");
        User u = service.getUserByName(request.getParameter("userLoggedIn"));
        boolean isPublishing = Boolean.parseBoolean(request.getParameter("isPublishing"));
        int blogId = Integer.parseInt(request.getParameter("hiddenBlogPostID"));
        Comment c = new Comment();
        c.setUser(u);
        c.setBody(commentBody);
        c.setPublished(isPublishing);
        c.setBlogPostId(blogId);
        c.setCommentTime(LocalDateTime.now());
        try {
            service.addComment(c);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }

}




