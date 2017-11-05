package com.ofn.service;

import com.ofn.dao.impl.DBMaintenanceDao;
import com.ofn.dao.impl.PersistenceException;
import com.ofn.dao.interfaces.*;
import com.ofn.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlogServiceImpl implements BlogService{

    private UserDao uDao;
    private CategoryDao cDao;
    private PageDao pDao;
    private BlogPostDao bpDao;
    private CommentDao coDao;
    private DBMaintenanceDao mDao;

    public BlogServiceImpl(UserDao uDao, CategoryDao cDao, PageDao pDao,
                           BlogPostDao bpDao, CommentDao coDao, DBMaintenanceDao mDao){
        this.uDao = uDao;
        this.cDao = cDao;
        this.pDao = pDao;
        this.bpDao = bpDao;
        this.coDao = coDao;
        this.mDao = mDao;
    }

    @Override
    public void refresh(){
        this.mDao.refresh();
    }

    @Override
    public List<Tag> getAllTags() {
        return bpDao.getAllTags();
    }

    @Override
    public boolean addTag(Tag tag) throws PersistenceException {
        boolean isAdded = bpDao.addTag(tag);
        if(!isAdded){
            throw new PersistenceException("Couldn't add tag");
        }
        return isAdded;
    }

    @Override
    public List<Category> getAllCategories() {
        return cDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int catId) {
        return cDao.getCategory(catId);
    }

    @Override
    public Category addCategory(Category category) throws PersistenceException {
        Category cat = cDao.addCategory(category);
        if(cat == null){
            throw new PersistenceException("Couldn't add category");
        }
        return cat;
    }

    @Override
    public Category updateCategory(Category category) throws PersistenceException {
        boolean isUpdated = cDao.updateCategory(category);
        if(!isUpdated) {
            throw new PersistenceException("Couldn't update category");
        }
        else{
            Category cat = cDao.getCategory(category.getCategoryID());
            if(cat == null) {
                throw new PersistenceException("Error retrieving updated category");
            }
            return cat;
        }
    }

    @Override
    public boolean removeCategory(int catId) throws PersistenceException {
        boolean isDeleted = cDao.removeCategory(catId);
        if(!isDeleted){
            throw new PersistenceException("Couldn't delete category");
        }
        return isDeleted;
    }

    @Override
    public List<User> getAllUsers() {
        return uDao.getAllUsers();
    }

    @Override
    public List<User> searchUsers(String... args) {
        return uDao.searchUsers(args);
    }

    @Override
    public User getUserById(int userId) {
        return uDao.getUserById(userId);
    }

    @Override
    public User addUser(User user) throws PersistenceException {
        User u = uDao.addUser(user);
        if(u == null){
            throw new PersistenceException("Error adding user");
        }
        return u;
    }

    @Override
    public User updateUser(User user) throws PersistenceException {
        boolean isUpdated = uDao.updateUser(user);
        if(!isUpdated){
            throw new PersistenceException("Error updating user");
        }
        return uDao.getUserById(user.getUserId());
    }

    @Override
    public boolean removeUser(int userId) throws PersistenceException {
        boolean isDeleted = uDao.removeUser(userId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting user");
        }
        return isDeleted;
    }

    @Override
    public List<Page> getPublishedPages() {
        List<Page> allPages = pDao.getAllPages();
        List<Page> retPages = new ArrayList<>();
        for(Page p : allPages){
            if(p.isPublished()){
                retPages.add(p);
            }
        }
        return retPages;
    }

    @Override
    public List<Page> getUnPublishedPages() {
        List<Page> allPages = pDao.getAllPages();
        List<Page> retPages = new ArrayList<>();
        for(Page p : allPages){
            if(!p.isPublished()){
                retPages.add(p);
            }
        }
        return retPages;
    }

    @Override
    public Page addPage(Page page) throws PersistenceException {
        Page p = pDao.addPage(page);
        if(p == null){
            throw new PersistenceException("Error adding page");
        }
        return p;
    }

    @Override
    public Page updatePage(Page page) throws PersistenceException {
        boolean isUpdated = pDao.updatePage(page);
        if(!isUpdated){
            throw new PersistenceException("Error updating page");
        }
        return pDao.getPage(page.getPageId());
    }

    @Override
    public Page removePage(int pageId) throws PersistenceException {
        Page p = pDao.getPage(pageId);
        boolean isDeleted = pDao.removePage(pageId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting page");
        }
        return p;
    }

    @Override
    public Page getPageById(int pageId) {
        return pDao.getPage(pageId);
    }

    @Override
    public Comment addComment(Comment comment) throws PersistenceException {
        Comment c = coDao.addComment(comment);
        if(c == null){
            throw new PersistenceException("Error adding comment");
        }
        return c;
    }

    @Override
    public Comment updateComment(Comment comment) throws PersistenceException {
        boolean isUpdated = coDao.updateComment(comment);
        if(!isUpdated){
            throw new PersistenceException("Error updating comment");
        }
        return coDao.getComment(comment.getCommentId());
    }

    @Override
    public Comment removeComment(int commentId) throws PersistenceException {
        Comment comment = coDao.getComment(commentId);
        boolean isDeleted = coDao.removeComment(commentId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting comment");
        }
        return comment;
    }

    @Override
    public Comment getCommentById(int commentId) {return coDao.getComment(commentId);}

    @Override
    public List<Comment> getCommentsForPost(int blogPostId){
        return coDao.getCommentsForPost(blogPostId);
    }

    @Override
    public List<BlogPost> getPublishedPosts() {
        return bpDao.getAllPubBlogPosts();
    }

    @Override
    public List<BlogPost> getUnPublishedPosts() {
        return bpDao.getAllUnPubBlogPosts();
    }

    @Override
    public List<BlogPost> getAllPosts() {
        List<BlogPost> allPub = bpDao.getAllPubBlogPosts();
        allPub.addAll(bpDao.getAllUnPubBlogPosts());
        return allPub;
    }

    @Override
    public List<BlogPost> searchBlogPost(String... args) {
        return bpDao.searchBlogPosts(args);
    }

    @Override
    public BlogPost addPost(BlogPost post) throws PersistenceException {
        BlogPost bp = bpDao.addBlogPost(post);
        if(bp == null){
            throw new PersistenceException("Error adding blog post");
        }
        return bp;
    }

    @Override
    public BlogPost updatePost(BlogPost post) throws PersistenceException {
        boolean isUpdated = bpDao.updateBlogPost(post);
        if(!isUpdated){
            throw new PersistenceException("Error updating blog post");
        }
        return post;
    }

    @Override
    public BlogPost removePost(int blogPostId) throws PersistenceException {
        BlogPost bd = bpDao.getBlogPostById(blogPostId);
        boolean isDeleted = bpDao.removeBlogPost(blogPostId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting blog post");
        }
        return bd;
    }

    @Override
    public BlogPost getBlogPost(int blogPostId) {
        return bpDao.getBlogPostById(blogPostId);
    }

    @Override
    public List<BlogPost> getByTags(String[] tags) {
        List<BlogPost> postsForTags = new ArrayList<>();
        for(String tag : tags){
            Tag t = new Tag();
            t.setTagText(tag);
            List<BlogPost> postsForTag = getPostsByTag(t);
            postsForTags.addAll(postsForTag);
        }
        return postsForTags;
    }

    @Override
    public List<BlogPost> getPostsByUserId(int userId) {
        return bpDao.getByUser(userId);
    }

    @Override
    public List<BlogPost> getPostsByTag(Tag tag) {
//        yet to implement;
        List<BlogPost> postsByTag = bpDao.getBlogPostsByTag(tag);
        return postsByTag;
    }

    @Override
    public Map<Integer, String> getPageLinks(){
        return pDao.getLinks();

    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return coDao.getCommentsByUserId(userId);
    }
}
