package com.ofn.controller;

import com.ofn.model.BlogPost;
import com.ofn.model.Comment;
import com.ofn.model.Page;
import com.ofn.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RESTController {
    private BlogService service;

    public RESTController(BlogService service){
        this.service = service;
    }

    @RequestMapping(value = "/displayStaticPage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Page displayStaticPage(@PathVariable("id") int id) {
        return service.getPageById(id);
    }

    @RequestMapping(value = "/getCommentsForBlogPost/{id}/{isOwner}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> getCommentsForBlogPost(@PathVariable("id") int id,
                                                @PathVariable("isOwner") boolean isOwner){

        return service.getCommentsForPost(id, isOwner);
    }

    @RequestMapping(value = "/displayBlogPost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BlogPost displayBlogPost(@PathVariable("id") int id){
        return service.getBlogPost(id);
    }
}
