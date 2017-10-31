package com.ofn.dao.impl;

import com.ofn.dao.interfaces.BlogPostDao;
import com.ofn.dao.interfaces.CommentDao;
import com.ofn.model.BlogPost;
import com.ofn.model.Comment;
import com.ofn.model.Tag;
import com.ofn.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CommentDaoDbImplTest {

    private CommentDao dao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("CommentDao", CommentDao.class);
        List<Comment> commentDBEntries = dao.getAllComments();
        for(Comment comm : commentDBEntries) {
            dao.removeComment(comm.getCommentId());
        }
        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);

        mDao.refresh();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCommentsForPost() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        dao.addComment(comm);
        Comment comm2 = new Comment();
        comm2.setUser(u);
        comm2.setBlogPostId(1);
        comm2.setBody("Please do it. You'll thank me for it.");
        comm2.setCommentTime(LocalDateTime.now());
        comm2.setPublished(true);
        dao.addComment(comm2);
        List<Comment> commsForPost = dao.getCommentsForPost(1);
        assertEquals(2, commsForPost.size());
    }

    @Test
    public void addComment() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        Comment posted = dao.addComment(comm);
        assertEquals(posted.getUser().getUserId(), comm.getUser().getUserId());
        assertEquals(posted.getBody(), comm.getBody());
    }

    @Test
    public void removeComment() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        Comment posted = dao.addComment(comm);
        boolean isDeleted = dao.removeComment(posted.getCommentId());
        assertTrue(isDeleted);
    }

    @Test
    public void updateComment() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        Comment posted = dao.addComment(comm);
        posted.setBody("You must try it. It is the best... RUSH ever. SEE WHAT I DID THERE?!");
        comm.setCommentTime(LocalDateTime.now());
        boolean isUpdated = dao.updateComment(posted);
        assertTrue(isUpdated);
    }

    @Test
    public void getComment() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        dao.addComment(comm);
        Comment getComm = dao.getComment(comm.getCommentId());
        assertEquals(getComm.getCommentId(), comm.getCommentId());
        assertEquals(getComm.getBody(), comm.getBody());
    }

    @Test
    public void getCommentsByUserId() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        dao.addComment(comm);
        Comment comm2 = new Comment();
        comm2.setUser(u);
        comm2.setBlogPostId(1);
        comm2.setBody("Please do it. You'll thank me for it.");
        comm2.setCommentTime(LocalDateTime.now());
        comm2.setPublished(true);
        dao.addComment(comm2);
        List<Comment> commsForUser = dao.getCommentsByUserId(1);
        assertEquals(2, commsForUser.size());
    }

    @Test
    public void getRandomComment() throws Exception {
        Comment comm = new Comment();
        User u = new User();
        u.setUserId(1);
        comm.setUser(u);
        comm.setBlogPostId(1);
        comm.setBody("Seriously, if you haven't tried it, you don't know what you're missing!");
        comm.setCommentTime(LocalDateTime.now());
        comm.setPublished(true);
        dao.addComment(comm);
        Comment comm2 = new Comment();
        comm2.setUser(u);
        comm2.setBlogPostId(1);
        comm2.setBody("Please do it. You'll thank me for it.");
        comm2.setCommentTime(LocalDateTime.now());
        comm2.setPublished(true);
        dao.addComment(comm2);
        Comment rando = dao.getRandomComment();
        assertNotNull(rando);
        assertTrue(rando.getCommentId() == comm.getCommentId() ||
                rando.getCommentId() == comm2.getCommentId());
    }

}