package com.init.luckyfriend.activity.NearByTinder;

/**
 * Created by nirav on 05/10/15.
 */
public class Data {



    public Data(){

    }

    public String getPerson_country() {
        return person_country;
    }

    public void setPerson_country(String person_country) {
        this.person_country = person_country;
    }

    public String getPerson_name() {

        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_profile_pic() {

        return person_profile_pic;
    }

    public void setPerson_profile_pic(String person_profile_pic) {
        this.person_profile_pic = person_profile_pic;
    }

    public String getPerson_id() {

        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    String person_id;
    String person_profile_pic;
    String person_name;
    String person_country;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    String last_name;


}

