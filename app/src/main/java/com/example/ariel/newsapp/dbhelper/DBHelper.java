package com.example.ariel.newsapp.dbhelper;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
    //Name of databases where the table will be
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsarticles.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the table with the columns specified in Contract. Make columns not null so they will
    //need to have some value in them
    @Override
    public void onCreate(SQLiteDatabase db){
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " (" +
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DATE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMGURL + " TEXT" +
                "); ";

        Log.d(TAG, "Creating SQL table: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table " + Contract.TABLE_ARTICLES.TABLE_NAME + " if exists;");
    }
}
