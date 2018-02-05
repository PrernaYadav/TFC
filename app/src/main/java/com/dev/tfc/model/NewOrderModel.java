package com.dev.tfc.model;

/**
 * Created by amit on 1/24/2018.
 */

public class NewOrderModel {

    String Price;
    String Qty;
    String Phone;
    String Username;
    String MenuName;
    String CollectionTime;
    String date;
    //String Logo;
    String Status;
    String UserIdNew;

    public String getUserIdNew() {
        return UserIdNew;
    }

    public void setUserIdNew(String userIdNew) {
        UserIdNew = userIdNew;
    }

    public String getResIdNew() {
        return ResIdNew;
    }

    public void setResIdNew(String resIdNew) {
        ResIdNew = resIdNew;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    String ResIdNew;
    String MenuId;

    public String getDelid() {
        return Delid;
    }

    public void setDelid(String delid) {
        Delid = delid;
    }

    String Delid;




    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        CollectionTime = collectionTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
