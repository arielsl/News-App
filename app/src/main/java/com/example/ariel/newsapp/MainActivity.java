package com.example.ariel.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private URL newsApi;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.textView);
        newsApi = NetworkUtils.mUriBuilder();
        new HTTPResponseTask().execute();
    }

    private class HTTPResponseTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            String results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(newsApi);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String results) {

            if(results != null && !results.equals("")){
                mTextView.setText(results);
            }
            else{

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
            new HTTPResponseTask().execute();
            return true;
        }else{
        }
        return super.onOptionsItemSelected(item);
    }
}
