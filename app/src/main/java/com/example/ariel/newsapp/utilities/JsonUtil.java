package com.example.ariel.newsapp.utilities;

import com.example.ariel.newsapp.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ariel on 6/29/2017.
 */

public class JsonUtil {
    private static final String ARTICLES = "articles";
    private static String AUTHOR = "author";
    private static String TITLE = "title";
    private static String DESCRIPTION = "description";
    private static String URL = "url";
    private static String IMAGE = "urlToImage";
    private static String DATE = "publishedAt";

    //Pass the url of the json file to parse it and return an array of the articles retrieved
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        //This array will hold all the articles in the json file
        ArrayList<NewsItem> newsArticles = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray(ARTICLES);
        //For each article in the json file get the values and pass them to a NewsItems class,
        //Then add the NewsItem to the array and return the array
        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);

            String author = article.getString(AUTHOR);
            String title = article.getString(TITLE);
            String description = article.getString(DESCRIPTION);
            String url = article.getString(URL);
            String imgUrl = article.getString(IMAGE);
            String published = article.getString(DATE);

            NewsItem newsItem = new NewsItem(author, title, description, url, imgUrl, published);
            newsArticles.add(newsItem);
        }

        return newsArticles;
    }
}
