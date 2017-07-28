package com.example.ariel.newsapp.utilities;

/**
 * Created by Ariel on 7/27/2017.
 */


import android.content.Context;
import android.support.annotation.NonNull;
import com.example.ariel.newsapp.tasks.NewsTask;

//Import the necessary Firebase job dispatchers
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class SchedulerUtils {
    private static final int REFRESH_TIME = 60;
    private static final String NEWS_JOB_TAG = "NewsTagJob";

    private static boolean schedulerInit;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        //Check if the job is currently running, if it is, return true and get out
        if(schedulerInit) {
            return;
        }

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                //Sets the job to run in the dispatcher
                .setService(NewsTask.class)

                //Sets the job tag, we select one we can remember to call it
                .setTag(NEWS_JOB_TAG)

                //We set the constraint to run the job every time we have internet connection
                .setConstraints(Constraint.ON_ANY_NETWORK)

                //The job should always execute to gather the most recent data
                .setLifetime(Lifetime.FOREVER)

                //Set the job to keep happening
                .setRecurring(true)

                //How often should the job trigger
                .setTrigger(Trigger.executionWindow(REFRESH_TIME, REFRESH_TIME))

                //Replace the last job with this one, as long as they have the same tag
                .setReplaceCurrent(true)

                //Build the job
                .build();

        //Set the initialization to true
        dispatcher.schedule(constraintRefreshJob);
        schedulerInit = true;
    }
}
