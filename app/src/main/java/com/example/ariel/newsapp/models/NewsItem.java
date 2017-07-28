package com.example.ariel.newsapp.models;

/**
 * Created by Ariel on 6/29/2017.
 */

//This class represents each news article
//It contains a variable for each item in the article
public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String url;
    private String image;
    private String date;

    //The class constructor
    public NewsItem(String authorIn, String titleIn, String descriptionIn, String urlIn, String imageIn, String dateIn){
        this.author = authorIn;
        this.title = titleIn;
        this.description = descriptionIn;
        this.url = urlIn;
        this.image = imageIn;
        this.date = dateIn;
    }

    //Getters are used to get the info for each article when needed
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setUrlToImage(String imageIn) {
        this.image = imageIn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateIn) {
        this.date = dateIn;
    }
}