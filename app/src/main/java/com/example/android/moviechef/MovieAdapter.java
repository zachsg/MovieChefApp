package com.example.android.moviechef;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.android.moviechef.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by zachg on 5/6/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Movie[] mMoviesData;

    private Context mContext;

    /**
     * Allow Activity to interact with the RecyclerView.
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * Interface for handling clicks of given movie item.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(listItemId, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        // Get current movie
        Movie currentMovie = mMoviesData[position];

        // Get context and current ImageView for loading photo
        Context context = holder.getmImageView().getContext();
        ImageView imageView = holder.getmImageView();

        // Load image from the current movie into the proper ImageView
        NetworkUtils.loadImage(context, imageView, currentMovie);
    }

    @Override
    public int getItemCount() {
        if (mMoviesData == null) {
            return 0;
        }

        return mMoviesData.length;
    }

    public void setmMoviesData(Movie[] movies) {
        mMoviesData = movies;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
                                     implements OnClickListener {

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
            String baseUrl = mContext.getString(R.string.base_url);
            String imageSize = mContext.getString(R.string.image_size_url);
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
