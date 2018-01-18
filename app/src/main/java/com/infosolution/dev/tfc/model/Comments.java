package com.infosolution.dev.tfc.model;

/**
 * Created by amit on 1/18/2018.
 */

public class Comments {

    int profileimage;
    String username;
    String comments;
    float rating;

    public Comments(int profileimage, String username, String comments, float rating) {
        this.profileimage = profileimage;
        this.username = username;
        this.comments = comments;
        this.rating = rating;
    }

    public int getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(int profileimage) {
        this.profileimage = profileimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
