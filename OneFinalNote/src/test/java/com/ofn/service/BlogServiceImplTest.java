package com.ofn.service;

import com.ofn.dao.impl.DBMaintenanceDao;
import com.ofn.dao.interfaces.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class BlogServiceImplTest {

    private UserDao uDao;
    private CategoryDao cDao;
    private PageDao pDao;
    private BlogPostDao bpDao;
    private CommentDao coDao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        uDao = ctx.getBean("UserDao", UserDao.class);
        cDao = ctx.getBean("CategoryDao", CategoryDao.class);
        pDao = ctx.getBean("PageDao", PageDao.class);
        bpDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        coDao = ctx.getBean("CommentDao", CommentDao.class);
        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);

        mDao.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllTags() throws Exception {
    }

    @Test
    public void addTag() throws Exception {
    }

    @Test
    public void getAllCategories() throws Exception {
    }

    @Test
    public void getCategoryById() throws Exception {
    }

    @Test
    public void addCategory() throws Exception {
    }

    @Test
    public void updateCategory() throws Exception {
    }

    @Test
    public void removeCategory() throws Exception {
    }

    @Test
    public void getAllUsers() throws Exception {
    }

    @Test
    public void searchUsers() throws Exception {
    }

    @Test
    public void getUserById() throws Exception {
    }

    @Test
    public void addUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void removeUser() throws Exception {
    }

    @Test
    public void getPublishedPages() throws Exception {
    }

    @Test
    public void getUnPublishedPages() throws Exception {
    }

    @Test
    public void addPage() throws Exception {
    }

    @Test
    public void updatePage() throws Exception {
    }

    @Test
    public void removePage() throws Exception {
    }

    @Test
    public void getPageById() throws Exception {
    }

    @Test
    public void addComment() throws Exception {
    }

    @Test
    public void updateComment() throws Exception {
    }

    @Test
    public void removeComment() throws Exception {
    }

    @Test
    public void getCommentById() throws Exception {
    }

    @Test
    public void getPublishedPosts() throws Exception {
    }

    @Test
    public void getUnPublishedPosts() throws Exception {
    }

    @Test
    public void getAllPosts() throws Exception {
    }

    @Test
    public void searchBlogPost() throws Exception {
    }

    @Test
    public void addPost() throws Exception {
    }

    @Test
    public void updatePost() throws Exception {
    }

    @Test
    public void removePost() throws Exception {
    }

    @Test
    public void getBlogPost() throws Exception {
    }

    @Test
    public void getByTags() throws Exception {
    }

    @Test
    public void getPostsByUserId() throws Exception {
    }

    @Test
    public void getPostsByTag() throws Exception {
    }

    @Test
    public void getCommentsByUserId() throws Exception {
    }

}