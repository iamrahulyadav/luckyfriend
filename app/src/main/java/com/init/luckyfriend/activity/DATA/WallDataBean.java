package com.init.luckyfriend.activity.DATA;

/**
 * Created by sanjay on 12/30/2015.
 */
public class WallDataBean
{
    public int type;

    String user_name;
    String last_name;
    String person_country;
    String post_img;
    int post_likes;
    int post_comments;
    String post_id;
    String like_person_id;
    String peron_dob;
    Boolean isLiked;

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public String getPeron_dob() {
        return peron_dob;
    }

    public void setPeron_dob(String peron_dob) {
        this.peron_dob = peron_dob;
    }

    public String getLike_person_id() {
        return like_person_id;
    }

    public void setLike_person_id(String like_person_id) {
        this.like_person_id = like_person_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    String person_id;
    String user_id;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPerson_profile_img() {
        return person_profile_img;
    }

    public void setPerson_profile_img(String person_profile_img) {
        this.person_profile_img = person_profile_img;
    }

    String person_profile_img;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPerson_country() {
        return person_country;
    }

    public void setPerson_country(String person_country) {
        this.person_country = person_country;
    }




    public WallDataBean(){
    }

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

    public WallDataBean(int type){

        this.type = type;
        //      this.modelContent = modelContent;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }






}
