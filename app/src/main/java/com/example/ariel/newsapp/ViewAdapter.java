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


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.NewsHolder>{
    private ArrayList<NewsItem> articlesList;
    NewsClickListener listener;

    public ViewAdapter(ArrayList<NewsItem> articlesList, NewsClickListener listener){
        this.articlesList = articlesList;
        this.listener = listener;

    }

    public interface NewsClickListener{
        void onNewsClick(int clickedNewsIndex);
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
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

    @Override
    public int getItemCount(){
        return articlesList == null ? 0 : articlesList.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitleText;
        TextView mDescriptionText;
        TextView mTimeText;

        public NewsHolder(View view){
            super(view);

            mTitleText = (TextView) view.findViewById(R.id.news_title);
            mTimeText = (TextView) view.findViewById(R.id.news_time);
            mDescriptionText = (TextView) view.findViewById(R.id.news_description);

            view.setOnClickListener(this);
        }

        public void bind(int position){
            NewsItem news = articlesList.get(position);

            mTitleText.setText(news.getTitle());
            mDescriptionText.setText(news.getDescription());
            mTimeText.setText(news.getDate());
        }

        @Override
        public void onClick(View view){
            int pos = getAdapterPosition();
            listener.onNewsClick(pos);
        }
    }
}
