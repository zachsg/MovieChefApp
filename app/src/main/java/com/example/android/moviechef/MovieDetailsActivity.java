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

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private TextView mTitleTV;
    private ImageView mCoverIV;
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

        /*
         * If network is up & we have a valid movie ID, fetch the additional details
         * on the movie that we need from the server, otherwise just return what we
         * already know about the given film.
         */
        mProgressBar.setVisibility(View.INVISIBLE);
        if (NetworkUtils.isNetworkUp(this) && movie.getmId() != null) {
            // Need to fetch runtime/duration details from server :/
            loadMovieDetails(movie);
        } else {
            checkAndSet(this, movie);
        }
    }

    private void loadMovieDetails(Movie movie) {
        mProgressBar.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.INVISIBLE);
        mTitleTV.setVisibility(View.INVISIBLE);

        new FetchDetailsTask().execute(movie.getmId());
    }

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
            mDurationTV.setText(movie.getmDuration());
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
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }

            try {
                return JsonUtils.getMovieDetails(MovieDetailsActivity.this, jsonResponse);
            } catch (JSONException jse) {
                jse.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie movie) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTitleTV.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.VISIBLE);

            checkAndSet(MovieDetailsActivity.this, movie);
        }
    }
}
