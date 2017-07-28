package com.example.ariel.newsapp;

/**
 * Created by Ariel on 6/29/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ariel.newsapp.models.NewsItem;
import java.util.ArrayList;

//All the new imports we will need to properly get the news info
import com.example.ariel.newsapp.dbhelper.Contract;
import com.squareup.picasso.Picasso;
import android.database.Cursor;
import android.widget.ImageView;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.NewsHolder>{
    private ArrayList<NewsItem> articlesList;
    NewsClickListener listener;
    //We need a context to change the view with the image and a cursor to get the info
    //from the database
    private Context context;
    Cursor cursor;

    //Now the constructor will take the cursor to the database to be able to read the news data
    public ViewAdapter(Cursor cursor, NewsClickListener listener){
        this.cursor = cursor;
        this.listener = listener;

    }

    //We change the interface to include the cursor to the database to read
    public interface NewsClickListener{
        void onNewsClick(Cursor cursor, int clickedNewsIndex);
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(R.layout.article, viewGroup, attachToParentImmediately);
        NewsHolder holder = new NewsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int position){
        newsHolder.bind(position);
    }

    //We change the count to be the number of elements in the database cursor pointing to the news
    //articles
    @Override
    public int getItemCount(){
        return cursor.getCount();
    }


    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitleText;
        TextView mDescriptionText;
        TextView mTimeText;
        //This image variable will draw the news image using picasso
        ImageView mImage;

        public NewsHolder(View view){
            super(view);

            mTitleText = (TextView) view.findViewById(R.id.news_title);
            mTimeText = (TextView) view.findViewById(R.id.news_time);
            mDescriptionText = (TextView) view.findViewById(R.id.news_description);

            //We get the reference to the image to be able to upload it to the view
            mImage = (ImageView) view.findViewById(R.id.news_image);

            view.setOnClickListener(this);
        }

        //We change the bind method to get the items in the cursor instead of an array
        public void bind(int position){
            //Get the element in the position according to the variable
            cursor.moveToPosition(position);

            //Use the references to the elements in the view to set their values to the values
            //in the database for the respective news article
            mTitleText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            mDescriptionText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            mTimeText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DATE)));

            //Get the url of the image in the database
            String imageUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMGURL));

            //If we succesfully get the url of the image, use picasso to upload the image to the view
            if (imageUrl != null){
                Picasso.with(context).load(imageUrl).into(mImage);
            }
        }

        //As above, when an item is clicked we pass the cursor to the database
        @Override
        public void onClick(View view){
            int pos = getAdapterPosition();
            listener.onNewsClick(cursor, pos);
        }
    }
}
