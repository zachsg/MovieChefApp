package com.example.android.moviechef;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.moviechef.utilities.JsonUtils;
import com.example.android.moviechef.utilities.NetworkUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

/**
 * For when a movie is being viewed in the details view.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    // Holds the movie's cover photo/image.
    private ImageView mCoverIV;

    private TextView mTitleTV;
    private TextView mReleaseDateTV;
    private TextView mDurationTV;
    private TextView mRatingTV;
    private Button mFaveButton;
    private TextView mOverviewTV;

    private ProgressBar mProgressBar;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Get the specific movie passed from parent
        Movie movie = (Movie) getIntent().getExtras().getSerializable("movie");

        mTitleTV = (TextView) findViewById(R.id.tv_movie_title);
        mCoverIV = (ImageView) findViewById(R.id.iv_movie_cover);
        mReleaseDateTV = (TextView) findViewById(R.id.tv_release_date);
        mDurationTV = (TextView) findViewById(R.id.tv_duration);
        mRatingTV = (TextView) findViewById(R.id.tv_rating);
        mFaveButton = (Button) findViewById(R.id.button_fave);
        mOverviewTV = (TextView) findViewById(R.id.tv_overview);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_details);
        mScrollView = (ScrollView) findViewById(R.id.sv_details);

        // Ensure progress bar is hidden no matter what until we decide whether to fetch data.
        mProgressBar.setVisibility(View.INVISIBLE);

        /*
         * If network is up & we have a valid movie ID, fetch the additional details
         * on the movie that we need from the server, otherwise just return what we
         * already know about the given film.
         */
        if (NetworkUtils.isNetworkUp(this) && movie.getmId() != null) {
            // Need to fetch runtime/duration details from server :/
            loadMovieDetails(movie);
        } else {
            /* No network connection available (or no Movie ID for some reason)
             * so just load whatever data we already know we have.
             */
            checkAndSet(this, movie);
        }
    }

    /**
     * Helper method to setup UI & kick off background task to fetch new data.
     * @param movie The movie for which to fetch new data.
     */
    private void loadMovieDetails(Movie movie) {
        // Show the progress bar.
        mProgressBar.setVisibility(View.VISIBLE);

        // Hide the other elements of the view until we've loaded data.
        mScrollView.setVisibility(View.INVISIBLE);
        mTitleTV.setVisibility(View.INVISIBLE);

        // Kick off background task with the specific Movie ID.
        new FetchDetailsTask().execute(movie.getmId());
    }

    /**
     * Validation of Movie data and subsequent setting of UI with that data.
     * @param context The context of the caller (MovieDetailsActivity in this case).
     * @param movie The specific movie being used to populate the UI.
     */
    private void checkAndSet(Context context, Movie movie) {
        if (movie.getmImageUrl() != null) {
            NetworkUtils.loadImage(context, mCoverIV, movie);
        }
        if (movie.getmTitle() != null) {
           mTitleTV.setText(movie.getmTitle());
        }
        if (movie.getmReleaseDate() != null) {
            mReleaseDateTV.setText(movie.getmReleaseDate().split("-")[0]);
        }
        if (movie.getmDuration() != null) {
            mDurationTV.setText(movie.getmDuration() + "min");
        }
        if (movie.getmAvgRating() != null ) {
            mRatingTV.setText(movie.getmAvgRating().toString() + "/10");
        }
        if (movie.getmOverview() != null) {
            mOverviewTV.setText(movie.getmOverview());
        }
    }

    private class FetchDetailsTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... params) {
            URL url = null;

            // If movie ID was passed in, proceed.  Otherwise do nothing.
            if (params.length == 1) {
                url = NetworkUtils.buildUrl(params[0]);
            } else {
                return null;
            }

            String jsonResponse = "";
            try {
                // Get the JSON from the server for our Movie-specific URL.
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }

            try {
                // Return the Movie object with the info pulled from the server.
                return JsonUtils.getMovieDetails(jsonResponse);
            } catch (JSONException jse) {
                jse.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie movie) {
            // Data pulled, hid the progress bar.
            mProgressBar.setVisibility(View.INVISIBLE);

            // Background task is complete, show the previously hidden UI elements.
            mTitleTV.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.VISIBLE);

            // Set the Views to have the newly fetched Movie data.
            checkAndSet(MovieDetailsActivity.this, movie);
        }
    }
}
