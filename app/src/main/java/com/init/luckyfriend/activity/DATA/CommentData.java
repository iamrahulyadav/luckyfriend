package com.init.luckyfriend.activity.DATA;

import java.io.Serializable;

/**
 * Created by sanjay on 12/6/2015.
 */
public class CommentData implements Serializable {
private String uname;
    String ctime;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCommenttxxt() {
        return commenttxxt;
    }

    public void setCommenttxxt(String commenttxxt) {
        this.commenttxxt = commenttxxt;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    private String commenttxxt;
    private String profilepic;
}
