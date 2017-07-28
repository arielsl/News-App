package com.example.ariel.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.ariel.newsapp.dbhelper.Contract;
import com.example.ariel.newsapp.dbhelper.DBHelper;
import com.example.ariel.newsapp.dbhelper.DatabaseUtils;
import com.example.ariel.newsapp.tasks.FetchNewstask;
import com.example.ariel.newsapp.utilities.SchedulerUtils;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, ViewAdapter.NewsClickListener{

    static final String TAG = "MAIN_ACTIVITY";
    private RecyclerView recyclerView;
    private ViewAdapter startAdapter;
    private static final int NEWS_LOADER = 1;

    //These new variables will help us read the database for its data
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "CREATED ACTIVITY AND TOOK LAYOUT");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //From the shared preferences, check if the app has run in the phone before, if it has not,
        //run the loader to and mark it as already ran
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = sp.getBoolean("firstRun", true);
        if(firstRun){
            load();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("firsRun", false);
            editor.apply();
        }
        Log.v(TAG, "CHECKED PREFERENCES AND RESULTED IN FIRST RUN = " + firstRun);

        //Call the job dispatcher to run the refresh method with the given intervals
        SchedulerUtils.scheduleRefresh(this);
    }

    //When the activity starts, get all the news info from the database and pass it to the adapter
    //to create each news view
    @Override
    protected void onStart(){
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        Log.v(TAG, "GOT NEWS FROM DATABASE, SIZE = " + cursor.getCount());
        startAdapter = new ViewAdapter(cursor, this);
        recyclerView.setAdapter(startAdapter);
    }

    //When the activity stops make sure to close the database connection
    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //When the refresh button is pressed, reread the database
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh) {
            load();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //We change the Async to be a loader
    public Loader<Void> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<Void>(this) {

            @Override
            public void onStartLoading(){
                super.onStartLoading();
            }

            //On the background thread, use the fetch news article method to retrieve the info
            //of the articles from the API
            @Override
            public Void loadInBackground() {
                FetchNewstask.fetchNewsToDatabase(MainActivity.this);
                return null;
            }
        };
    }

    //When the loader has finished, read the database and pass the cursor to the adapter to
    //populate the view
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        startAdapter = new ViewAdapter(cursor, this);
        recyclerView.setAdapter(startAdapter);
        startAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> data){

    }

    //When an article is clicked this method will be called, we get the position of the item
    //and we pass it to the cursor to read that position in the database.
    //We get the link of the article from the database and we start an intent with the url
    @Override
    public void onNewsClick(Cursor cursor, int clickedItem){
        cursor.moveToPosition(clickedItem);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //Reload when called
    public void load(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }
}
