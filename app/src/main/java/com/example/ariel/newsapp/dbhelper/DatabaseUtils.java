package com.example.ariel.newsapp.dbhelper;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
//Import info for column names, and the news item class to build ech article when we retrieve info
import static com.example.ariel.newsapp.dbhelper.Contract.TABLE_ARTICLES.*;
import com.example.ariel.newsapp.models.NewsItem;

public class DatabaseUtils {
    //Query that gets the cursor to all the news articles in the table, sorted by the date they were
    //published
    public static Cursor getAll(SQLiteDatabase db) {
        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_DATE + " DESC"
        );
    }

    //Query that takes a list of articles to insert to the table, it loops through the list of
    //articles and on by one inserts them
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles){
        db.beginTransaction();
        try{
            for(NewsItem news : articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, news.getTitle());
                cv.put(COLUMN_NAME_AUTHOR, news.getAuthor());
                cv.put(COLUMN_NAME_DESCRIPTION, news.getDescription());
                cv.put(COLUMN_NAME_DATE, news.getDate());
                cv.put(COLUMN_NAME_URL, news.getUrl());
                cv.put(COLUMN_NAME_IMGURL, news.getImage());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    //Query that deletes all items in the table
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }
}
