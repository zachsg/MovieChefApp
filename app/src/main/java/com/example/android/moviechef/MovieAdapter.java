package com.example.android.moviechef;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by zachg on 5/6/17.
 */

public class MovieAdapter extends RecyclerView.Adapter {

    private Movie[] mMoviesData;

    /**
     * Allow Activity to interact with the RecyclerView.
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * Interface for handling clicks of given movie item.
     */
    private interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO: fill out onCreateViewHolder
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO: fill out onBindViewHolder
    }

    @Override
    public int getItemCount() {
        // TODO: fill out getItemCount
        return 0;
    }

    private class MovieAdapterViewHolder extends RecyclerView.ViewHolder
                                      implements View.OnClickListener {

        private final ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_movie_cover);
            view.setOnClickListener(this);
        }

        public ImageView getmImageView() {
            return mImageView;
        }

        @Override
        public void onClick(View v) {
            // TODO: fill out onClick
        }
    }
}
