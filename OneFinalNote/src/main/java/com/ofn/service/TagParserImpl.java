/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ofn.service;

import com.ofn.dao.interfaces.BlogPostDao;
import com.ofn.model.BlogPost;
import com.ofn.model.Tag;
import java.util.ArrayList;

/**
 *
 * @author Hayden
 */
public class TagParserImpl implements TagParser{
    
    BlogPostDao dao;

    public TagParserImpl(BlogPostDao dao) {
        this.dao = dao;
    }
    
    
    //further implementation: make the hashtag text bold or a URL?
    public BlogPost parseTags(BlogPost bp){
        String[] beginWithHT = bp.getBody().split("#");
        ArrayList<Tag> tagList = new ArrayList<>();
        for(String s: beginWithHT){
            String[] oneAndGarbage = s.split(" ");
            Tag toAdd = new Tag();
            toAdd.setTagText(oneAndGarbage[0]);
            tagList.add(toAdd);
        }
        
        
        bp.setTagList(tagList);
      
        //I skipeed this line for testing, since we know the DAO works
        dao.updateBlogPost(bp);
        
        return bp;
    }
}
