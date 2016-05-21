package com.init.luckyfriend.activity.DATA;

/**
 * Created by user on 2/19/2016.
 */
public class FavouriteDataBean {
    String post_img;
    int post_likes;
    int post_comments;
    String post_user_first_name;
    String post_user_last_name;
    String post_user_profile_pic;
    String post_user_dob;
    String post_user_country;
    int isliked;
    String person_id;
    int isfriend;

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public int getIsliked() {
        return isliked;
    }

    public void setIsliked(int isliked) {
        this.isliked = isliked;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    String post_id;

    public int getPost_comments() {
        return post_comments;
    }

    public String getPost_user_country() {
        return post_user_country;
    }

    public void setPost_user_country(String post_user_country) {
        this.post_user_country = post_user_country;
    }

    public String getPost_user_dob() {

        return post_user_dob;
    }

    public void setPost_user_dob(String post_user_dob) {
        this.post_user_dob = post_user_dob;
    }

    public String getPost_user_profile_pic() {

        return post_user_profile_pic;
    }

    public void setPost_user_profile_pic(String post_user_profile_pic) {
        this.post_user_profile_pic = post_user_profile_pic;
    }

    public String getPost_user_last_name() {

        return post_user_last_name;
    }

    public void setPost_user_last_name(String post_user_last_name) {
        this.post_user_last_name = post_user_last_name;
    }

    public String getPost_user_first_name() {

        return post_user_first_name;
    }

    public void setPost_user_first_name(String post_user_first_name) {
        this.post_user_first_name = post_user_first_name;
    }

    public void setPost_comments(int post_comments) {
        this.post_comments = post_comments;
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




}

