package com.example.ariel.newsapp.utilities;

/**
 * Created by Ariel on 6/25/2017.
 */

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class NetworkUtils {
    final static String base_url = "newsapi.org";
    final static String path1 = "v1";
    final static String path2 = "articles";
    final static String query1 = "source";
    final static String query2 = "sortBy";
    final static String query3 = "apiKey";
    final static String query_parameter1 = "the-next-web";
    final static String query_parameter2 = "latest";
    //Paste unique News API key into query_parameter3
    final static String query_parameter3 = "2c531120bd4740dd919b9ca945ec03ac";
    //Help for URI builder from https://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
    public static URL mUriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(base_url)
                .appendPath(path1)
                .appendPath(path2)
                .appendQueryParameter(query1, query_parameter1)
                .appendQueryParameter(query2, query_parameter2)
                .appendQueryParameter(query3, query_parameter3);

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
