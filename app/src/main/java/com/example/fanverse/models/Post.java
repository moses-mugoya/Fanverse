package com.example.fanverse.models;

public class Post {
    private String username;
    private String caption;
    private String image;
    private String datetime;

    public Post(){}

    public Post(String username, String caption, String image, String datetime) {
        this.username = username;
        this.caption = caption;
        this.image = image;
        this.datetime = datetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
