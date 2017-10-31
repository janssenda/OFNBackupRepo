package com.ofn.dao.impl;

import com.ofn.dao.interfaces.BlogPostDao;
import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.model.BlogPost;
import com.ofn.model.BlogPostTag;
import com.ofn.model.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlogPostDaoDbImplTest {

    private BlogPostDao dao;
    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        List<BlogPostTag> allBlogPostTags = dao.getAllBlogPostTagBridgeEntries();
        for(BlogPostTag abpt : allBlogPostTags){
            dao.removeBlogPostBridgeEntries(abpt.getBlogPostID(), abpt.getTagText());
        }
        List<BlogPost> allPosts = dao.getAllPubBlogPosts();
        allPosts.addAll(dao.getAllUnPubBlogPosts());
        for(BlogPost ap : allPosts){
            dao.removeBlogPost(ap.getBlogPostId());
        }
        List<Tag> allTags = dao.getAllTags();
        for(Tag t : allTags){
            dao.removeTag(t.getTagText());
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getRandomBlogPost() throws Exception {
    }

    @Test
    public void getAllTags() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("metal");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("hippie");
        dao.addTag(t2);
        List<Tag> at = dao.getAllTags();
        assertEquals(2, at.size());
    }

    @Test
    public void addTag() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("progrock");
        boolean isAdded = dao.addTag(t1);
        assertTrue(isAdded);
    }

    @Test
    public void removeTag() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("metal");
        dao.addTag(t1);
        boolean isRemoved = dao.removeTag(t1.getTagText());
        assertTrue(isRemoved);
    }

    @Test
    public void addBlogPost() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        BlogPost added = dao.addBlogPost(bp);
        assertEquals(added.getBody(), bp.getBody());
    }

    @Test
    public void updateBlogPost() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        BlogPost added = dao.addBlogPost(bp);
        added.setEndDate(bp.getEndDate().plusYears(1));
        added.setStatus();
        added.setBody("<html>Meshuggah is probably the loudest band on earth, bar none</html>");
        Tag t3 = new Tag();
        t3.setTagText("brutal");
        dao.addTag(t3);
        List<Tag> postTags = bp.getTagList();
        postTags.add(t3);
        added.setTagList(postTags);
        boolean isUpdated = dao.updateBlogPost(added);
        assertTrue(isUpdated);
    }

    @Test
    public void getBlogPostById() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        BlogPost added = dao.addBlogPost(bp);
        BlogPost getAdded = dao.getBlogPostById(added.getBlogPostId());
        assertEquals(getAdded.getBlogPostId(),added.getBlogPostId());
    }

    @Test
    public void getBlogPostsByTag() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> postsByTag = dao.getBlogPostsByTag(t1);
        assertEquals(1, postsByTag.size());
    }

    @Test
    public void removeBlogPost() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        bp = dao.addBlogPost(bp);
        boolean isDeleted = dao.removeBlogPost(bp.getBlogPostId());
        assertTrue(isDeleted);
    }

    @Test
    public void getAllPubBlogPosts() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> pubPosts = dao.getAllPubBlogPosts();
        assertEquals(1, pubPosts.size());
    }

    @Test
    public void getAllUnPubBlogPosts() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(false);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        //bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> unpubPosts = dao.getAllUnPubBlogPosts();
        assertEquals(1, unpubPosts.size());
    }

    @Test
    public void getByUser() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> postsByUser = dao.getByUser(1);
        assertEquals(1,postsByUser.size());
    }

    @Test
    public void getByCategory() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> postsByCat = dao.getByCategory(1);
        assertEquals(1, postsByCat.size());
    }

    @Test
    public void searchBlogPosts() throws Exception {
        Tag t1 = new Tag();
        t1.setTagText("Meshuggah");
        dao.addTag(t1);
        Tag t2 = new Tag();
        t2.setTagText("metal");
        dao.addTag(t2);
        BlogPost bp = new BlogPost();
        bp.setUserId(1);
        bp.setCategoryId(1);
        bp.setTitle("Meshuggah rules! \\m/\\m/");
        bp.setBody("<html>Meshuggah is probably the loudest band on earth</html>");
        List<Tag> blogTagList = new ArrayList<>();
        blogTagList.add(t1);
        blogTagList.add(t2);
        bp.setTagList(blogTagList);
        bp.setPublished(true);
        bp.setUpdateTime(LocalDateTime.now());
        bp.setStartDate(LocalDateTime.now());
        bp.setEndDate(LocalDateTime.now().plusYears(1));
        bp.setCommentList(new ArrayList<>());
        bp.setStatus();
        dao.addBlogPost(bp);
        List<BlogPost> findMyPost = dao.searchBlogPosts(null,"1",null,"Meshuggah rules! \\m/\\m/");
        assertEquals(1, findMyPost.size());
    }

}