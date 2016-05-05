package com.init.luckyfriend.activity.MyPost;

import java.io.Serializable;

/**
 * Created by sanjay on 12/30/2015.
 */
public class PostDataBean implements Serializable
{
    int post_comments;
    int post_likes;
    int isliked;

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    String person_id;

    public int getIsliked() {
        return isliked;
    }

    public void setIsliked(int isliked) {
        this.isliked = isliked;
    }

    String post_img;
    String post_id;
    String post_date;
    String last_name,user_name,person_profile_pic;

    public String getPerson_profile_pic() {
        return person_profile_pic;
    }

    public void setPerson_profile_pic(String person_profile_pic) {
        this.person_profile_pic = person_profile_pic;
    }

    public String getUser_name() {

        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLast_name() {

        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public PostDataBean(){
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_id() {

        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public int getPost_likes() {

        return post_likes;
    }

    public void setPost_likes(int post_likes) {
        this.post_likes = post_likes;
    }


    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public int getPost_comments() {

        return post_comments;
    }

    public void setPost_comments(int post_comments) {
        this.post_comments = post_comments;
    }



}
