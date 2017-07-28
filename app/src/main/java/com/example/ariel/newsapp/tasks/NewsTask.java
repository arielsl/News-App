package com.example.ariel.newsapp.tasks;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.os.AsyncTask;
import android.widget.Toast;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;

public class NewsTask extends JobService{

    private AsyncTask backgroundThread;

    //The first method ran when the job starts, it will run the main thread of our app
    @Override
    public boolean onStartJob(final JobParameters job){

        //Make the background thread to run an Async
        backgroundThread = new AsyncTask() {

            //Runs before the thread is executed
            @Override
            protected void onPreExecute(){
                //Makes a toast to tell the user the news have been updated
                Toast.makeText(NewsTask.this, "Most recent news have been added", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            //The actual fetching of the news happens in the background
            @Override
            protected Object doInBackground(Object[] params) {
                FetchNewstask.fetchNewsToDatabase(NewsTask.this);
                return null;
            }

            //After we finish retrieving all the data, we set the task as finished
            @Override
            protected void onPostExecute(Object o){
                jobFinished(job, false);
                super.onPostExecute(o);
            }
        };

        //We run the thread to start reading the news
        backgroundThread.execute();
        return true;
    }

    //Called when the job stops with the given parameters
    @Override
    public boolean onStopJob(JobParameters job){
        if (backgroundThread != null){
            backgroundThread.cancel(false);
        }
        return true;
    }
}

