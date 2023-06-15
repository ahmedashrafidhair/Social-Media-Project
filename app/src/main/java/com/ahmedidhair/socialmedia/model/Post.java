package com.ahmedidhair.socialmedia.model;

public class Post {
    private String postId;
    private String postImage;
    private String postTitle;
    private String postedBy;
    private String postDescription;
    private Long postedAt;
    private int postLike;

    public Post() {

    }

    public Post(String postId, String postImage, String postTitle, String postedBy, String postDescription, Long postedAt, int postLike) {
        this.postId = postId;
        this.postImage = postImage;
        this.postTitle = postTitle;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postedAt = postedAt;
        this.postLike = postLike;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Long postedAt) {
        this.postedAt = postedAt;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }
}
