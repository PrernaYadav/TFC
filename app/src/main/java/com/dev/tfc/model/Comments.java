package com.dev.tfc.model;

/**
 * Created by amit on 1/18/2018.
 */

public class Comments {

    String profileimage;
    String username;
    String comments;
    Float rating;

    public Comments() {
        this.profileimage = profileimage;
        this.username = username;
        this.comments = comments;
        this.rating = rating;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
