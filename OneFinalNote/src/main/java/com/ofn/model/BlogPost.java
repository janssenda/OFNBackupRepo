package com.ofn.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPost {

    private int blogPostId;
    private int userId;
    private String userName;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime updateTime;
    private String title;
    private int categoryId;
    private String body;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime startDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime endDate;
    private boolean published;

    private List<Comment> commentList;
    private List<Tag> tagList;

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<Tag> getTagList() {
        return tagList;
    }
    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
    public int getBlogPostId() {
        return blogPostId;
    }
    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }
    public List<Comment> getCommentList() {
        return commentList;
    }
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }


    public boolean setStatus(){

        LocalDateTime d = LocalDateTime.now();
        published = (d.compareTo(startDate) >= 0 & d.compareTo(endDate)<=0 );
        return published;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogPost blogPost = (BlogPost) o;

        if (blogPostId != blogPost.blogPostId) return false;
        if (userId != blogPost.userId) return false;
        if (categoryId != blogPost.categoryId) return false;
        if (published != blogPost.published) return false;
        if (updateTime != null ? !updateTime.equals(blogPost.updateTime) : blogPost.updateTime != null) return false;
        if (title != null ? !title.equals(blogPost.title) : blogPost.title != null) return false;
        if (body != null ? !body.equals(blogPost.body) : blogPost.body != null) return false;
        if (startDate != null ? !startDate.equals(blogPost.startDate) : blogPost.startDate != null) return false;
        if (endDate != null ? !endDate.equals(blogPost.endDate) : blogPost.endDate != null) return false;
        if (commentList != null ? !commentList.equals(blogPost.commentList) : blogPost.commentList != null)
            return false;
        return tagList != null ? tagList.equals(blogPost.tagList) : blogPost.tagList == null;
    }

    @Override
    public int hashCode() {
        int result = blogPostId;
        result = 31 * result + userId;
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (published ? 1 : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        return result;
    }
}
