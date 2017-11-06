/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ofn.service;

import com.ofn.model.BlogPost;
import com.ofn.model.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Hayden
 */
public class TagParserImplTest {

    private TagParser tp;


    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        tp = ctx.getBean("TagParser",TagParser.class);
    }


    /**
     * Test of parseTags method, of class TagParserImpl.
     */
    @Test
    public void testParseTags() {
        BlogPost orig = new BlogPost();
        orig.setBody("#music is great! I can't decide whether I prefer #corndog or #cornwolf");
        
        BlogPost fromService = tp.parseTags(orig);
        
        Tag music = new Tag();
        music.setTagText("#music");
        
        Tag corndog = new Tag();
        corndog.setTagText("#corndog");
        
        Tag cornwolf = new Tag();
        cornwolf.setTagText("#cornwolf");
        
        ArrayList<Tag> tagList = new ArrayList<>(Arrays.asList(music,corndog,cornwolf));
        orig.setTagList(tagList);
        
        assertEquals(orig,fromService);
        
    }

}
