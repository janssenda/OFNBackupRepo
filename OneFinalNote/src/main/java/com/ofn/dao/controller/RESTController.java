package com.ofn.dao.controller;

import com.ofn.model.Page;
import com.ofn.service.BlogService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RESTController {
    private BlogService service;

    public RESTController(BlogService service){
        this.service = service;
    }

    @RequestMapping(value = "/displayStaticPage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Page displayStaticPage(@PathVariable("id") int id){
        return service.getPageById(id);
    }
}
