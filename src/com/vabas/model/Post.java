package com.vabas.model;

import java.util.ArrayList;

public class Post {
    //Cash
    public static ArrayList<Post> postList;

    private int id;
    private String content;
    private String created;
    private String updated;
    private ArrayList<Label> postLabelList;
    private PostStatus postStatus;

    public Post(){

    }

    public Post(int id, String content, String created, String updated, ArrayList<Label> postLabelList, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postLabelList = postLabelList;
        this.postStatus = postStatus;
    }

    public Post(int id, String content) {
        this.id = id;
        this.content = content;
        this.created = null;
        this.updated = null;
        this.postLabelList = null;
        this.postStatus = PostStatus.UNDER_REVIEW;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", postLabelList=" + postLabelList +
                ", postStatus=" + postStatus +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public ArrayList<Label> getPostLabelList() {
        return postLabelList;
    }

    public void setPostLabelList(ArrayList<Label> postLabelList) {
        this.postLabelList = postLabelList;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}
