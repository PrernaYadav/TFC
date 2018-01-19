package com.infosolution.dev.tfc.model;

/**
 * Created by amit on 1/18/2018.
 */

public class Home {

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProimage() {
        return proimage;
    }

    public void setProimage(String proimage) {
        this.proimage = proimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAvailimg() {
        return availimg;
    }

    public void setAvailimg(String availimg) {
        this.availimg = availimg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    String proname;
    String  proimage;
    String username;
    String timing;
    String price;
    String quantity;
    String availimg;
    String count;
    String fav;

    public Home() {
        this.proname = proname;
        this.proimage = proimage;
        this.username = username;
        this.timing = timing;
        this.price = price;
        this.quantity = quantity;
        this.availimg = availimg;
        this.count = count;
        this.fav = fav;
    }
}
