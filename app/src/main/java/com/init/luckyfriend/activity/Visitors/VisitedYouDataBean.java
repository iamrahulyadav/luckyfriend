package com.init.luckyfriend.activity.Visitors;

/**
 * Created by user on 2/22/2016.
 */
public class VisitedYouDataBean {
    String person_profile_pic;
    String last_name;
    String user_name;
    String post_img;
    int post_likes;
    int post_comments;
    String person_country;
    int isliked;
    String post_id;
    String person_id;

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getPost_id() {

        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public int getIsliked() {

        return isliked;
    }

    public void setIsliked(int isliked) {
        this.isliked = isliked;
    }

    public String getPerson_country() {
        return person_country;
    }

    public void setPerson_country(String person_country) {
        this.person_country = person_country;
    }

    public String getPerson_dob() {
        return person_dob;
    }

    public void setPerson_dob(String person_dob) {
        this.person_dob = person_dob;
    }

    String person_dob;

    public int getPost_comments() {
        return post_comments;
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

    public String getPerson_profile_pic() {

        return person_profile_pic;
    }

    public void setPerson_profile_pic(String person_profile_pic) {
        this.person_profile_pic = person_profile_pic;
    }
}
