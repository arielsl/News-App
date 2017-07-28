package com.example.ariel.newsapp.tasks;

/**
 * Created by Ariel on 7/27/2017.
 */

import android.os.AsyncTask;
import android.widget.Toast;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;

public class NewsTask extends JobService{

    //The first method ran when the job starts, it will run the main thread of our app
    @Override
    public boolean onStartJob(final JobParameters job){
        return true;
    }

    //Called when the job stops with the given parameters
    @Override
    public boolean onStopJob(JobParameters job){
        return true;
    }
}

