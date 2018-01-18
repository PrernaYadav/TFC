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

    public int getProimage() {
        return proimage;
    }

    public void setProimage(int proimage) {
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

    public int getAvailimg() {
        return availimg;
    }

    public void setAvailimg(int availimg) {
        this.availimg = availimg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    String proname;
    int  proimage;
    String username;
    String timing;
    String price;
    String quantity;
    int availimg;
    String count;
    int fav;

    public Home(String proname, int proimage, String username, String timing, String price, String quantity, int availimg, String count, int fav) {
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
