package com.example.ariel.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.ariel.newsapp.utilities.NetworkUtils;
import com.example.ariel.newsapp.utilities.JsonUtil;
import com.example.ariel.newsapp.models.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "mainactivity";
    private RecyclerView recyclerView;
    private ViewAdapter startAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(startAdapter);
        new FetchNewsTask().execute();
    }


    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>>{
        @Override
        protected ArrayList<NewsItem> doInBackground(String... params){
            ArrayList<NewsItem> articleList = null;

            URL newsRequestUrl = NetworkUtils.mUriBuilder();

            try{
                String jsonNewsDataResponse = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);
                articleList = JsonUtil.parseJSON(jsonNewsDataResponse);

            } catch (IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }

            return articleList;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> articleList){
            super.onPostExecute(articleList);

            if(articleList!= null) {
                ViewAdapter adapter = new ViewAdapter(articleList, new ViewAdapter.NewsClickListener() {
                    @Override
                    public void onNewsClick(int clickedItemIndex) {
                        String url = articleList.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("URL CLICKED: %s", url));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

        }
    }

    //Refresh button idea from https://stackoverflow.com/questions/27822002/actionbar-refresh-button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh) {
            new FetchNewsTask().execute();
            return true;
        }else{
        }
        return super.onOptionsItemSelected(item);
    }
}
