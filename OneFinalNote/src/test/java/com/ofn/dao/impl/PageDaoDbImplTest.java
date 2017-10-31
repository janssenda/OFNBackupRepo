package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.dao.interfaces.PageDao;
import com.ofn.model.Category;
import com.ofn.model.Page;
import com.ofn.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PageDaoDbImplTest {

    private PageDao dao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("PageDao", PageDao.class);
        List<Page> pageDBEntries = dao.getAllPages();
        for(Page p : pageDBEntries){
            dao.removePage(p.getPageId());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addPage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        Timestamp testTS = Timestamp.valueOf(p.getUpdatedTime());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        Page addedPage = dao.addPage(p);
        assertEquals(1,addedPage.getUser().getUserId());
        assertEquals(addedPage.getTitle(), p.getTitle());
    }

    @Test
    public void updatePage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        Page addedPage = dao.addPage(p);
        addedPage.setTitle("my first edited static page");
        addedPage.setBody("dynamic html code for the new static code that is expanded");
        addedPage.setUpdatedTime(LocalDateTime.now());
        boolean isEdited = dao.updatePage(addedPage);
        assertTrue(isEdited);
    }

    @Test
    public void removePage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        Page addedPage = dao.addPage(p);
        boolean isDeleted = dao.removePage(addedPage.getPageId());
        assertTrue(isDeleted);
    }

    @Test
    public void getPage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        Page addedPage = dao.addPage(p);
        Page newPage = dao.getPage(addedPage.getPageId());
        assertEquals(newPage.getTitle(), addedPage.getTitle());
    }

    @Test
    public void getLinks() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        dao.addPage(p);
        p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my second static page");
        p.setUser(u);
        dao.addPage(p);
        p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        p.setUser(u);
        dao.addPage(p);
        Map<Integer,String> pageLinks = dao.getLinks();
        assertEquals(3,pageLinks.size());
    }

    @Test
    public void getAllPages() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        dao.addPage(p);
        p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my second static page");
        p.setUser(u);
        dao.addPage(p);
        List<Page> allPages = dao.getAllPages();
        assertEquals(2, allPages.size());
    }

}