package com.init.luckyfriend.activity.MyPost;

/**
 * Created by sanjay on 12/30/2015.
 */
public class PostDataBean
{
    public int type;
    public PostDataBean(){
    }

    public PostDataBean(int type){
        this.type = type;
        //      this.modelContent = modelContent;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Number getLikecount() {
        return likecount;
    }

    public void setLikecount(Number likecount) {
        this.likecount = likecount;
    }

    public Number getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Number commentcount) {
        this.commentcount = commentcount;
    }

    private String caption,imageurl;
private Number likecount,commentcount;
}
