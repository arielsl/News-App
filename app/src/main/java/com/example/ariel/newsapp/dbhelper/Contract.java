package com.example.ariel.newsapp.dbhelper;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.provider.BaseColumns;

public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns{
        //Table name to use when calling for information
        public static final String TABLE_NAME = "newsarticles";
        //Each string represents the column name in the table
        public static final String COLUMN_NAME_TITLE = "article_title";
        public static final String COLUMN_NAME_AUTHOR = "article_author";
        public static final String COLUMN_NAME_DATE = "article_date";
        public static final String COLUMN_NAME_DESCRIPTION = "article_description";
        public static final String COLUMN_NAME_URL = "article_url";
        public static final String COLUMN_NAME_IMGURL = "image_url";

    }
}
