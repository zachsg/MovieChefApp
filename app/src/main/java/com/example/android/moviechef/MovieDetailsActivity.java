package com.example.android.moviechef;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviechef.utilities.NetworkUtils;

import org.w3c.dom.Text;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private TextView mTitleTV;
    private ImageView mCoverIV;
    private TextView mReleaseDateTV;
    private TextView mDurationTV;
    private TextView mRatingTV;
    private Button mFaveButton;
    private TextView mOverviewTV;

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

        checkAndSet(this, movie);

    }

    private void checkAndSet(Context context, Movie movie) {
        if (movie.getmImageUrl() != null) {
            NetworkUtils.loadImage(context, mCoverIV, movie);
        }
        if (movie.getmTitle() != null) {
           mTitleTV.setText(movie.getmTitle());
        }
        if (movie.getmReleaseDate() != null) {
            mReleaseDateTV.setText(movie.getmReleaseDate());
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
}
