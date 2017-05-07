package com.example.android.moviechef;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by zachg on 5/6/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

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
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        // Get current movie's image URL
        Movie currentMovie = mMoviesData[position];
        String imageUrl = holder.getImageUrl(currentMovie);

        // Set proper image for given movie
        Context context = holder.getmImageView().getContext();
        ImageView imageView = holder.getmImageView();
        Picasso.with(context).load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (mMoviesData == null) {
            return 0;
        }

        return mMoviesData.length;
    }

    protected class MovieAdapterViewHolder extends RecyclerView.ViewHolder
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

        /**
         * Build & return the full URL for a given movie's cover image.
         * @param movie The movie for which the image will be built.
         * @return The full URL for given movie's cover image.
         */
        public String getImageUrl(Movie movie) {
            String baseUrl = Resources.getSystem().getString(R.string.base_url);
            String imageSize = Resources.getSystem().getString(R.string.image_size_url);
            String imageUrl = movie.getmImageUrl();

            return baseUrl + imageSize + imageUrl;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie currentMovie = mMoviesData[position];
            mClickHandler.onClick(currentMovie);
        }
    }
}
