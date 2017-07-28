package com.example.ariel.newsapp.tasks;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ariel.newsapp.dbhelper.DBHelper;
import com.example.ariel.newsapp.dbhelper.DatabaseUtils;
import com.example.ariel.newsapp.models.NewsItem;
import com.example.ariel.newsapp.utilities.NetworkUtils;
import com.example.ariel.newsapp.utilities.JsonUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchNewstask {
    //We read the most recent news from the JSON file and we pass them to the database
    public static void fetchNewsToDatabase(Context context){
        //The array will hold the articles so we can pass them to the bulk insert method
        ArrayList<NewsItem>  articles = null;
        //Build the URL for the JSON containing the news articles
        URL url = NetworkUtils.mUriBuilder();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try{
            //We do not need the previous articles, so we delete the contents in the table
            DatabaseUtils.deleteAll(db);

            //Parse the JSON file into the articles array
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            articles = JsonUtil.parseJSON(json);

            //Bulk insert the array of articles into the database
            DatabaseUtils.bulkInsert(db, articles);

            //If there is an error while reading or inserting the articles, catch it
        }catch(IOException e){
            Log.v("IOEXCEPTION", "Error while reading data");
            e.printStackTrace();
        }catch(JSONException e){
            Log.v("IOEXCEPTION", "Error while reading data");
            e.printStackTrace();
        }
    }
}
