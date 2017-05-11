package com.example.android.moviechef;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviechef.utilities.JsonUtils;
import com.example.android.moviechef.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieGridActivity extends AppCompatActivity
                            implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private GridLayoutManager mGridLayoutManager;
    private static final int SPAN_COUNT = 2;


    private TextView mEmptyStateView;
    private ProgressBar mProgressBar;

    private static final String POPULAR_MOVIES = "popular";
    private static final String TOP_RATED_MOVIES = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mEmptyStateView = (TextView) findViewById(R.id.tv_empty_state);

        mProgressBar = (ProgressBar) findViewById(R.id.pb);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_grid);

        // Set layout with 2 movies shown per row.
        mGridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMovieAdapter = new MovieAdapter(MovieGridActivity.this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // Check on network state (connected or not)
        if (!NetworkUtils.isNetworkUp(this)) {
            // No network, show the empty state and hide the Recycler.
            mEmptyStateView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            // Yes network, hide the empty state and show the Recycler.
            mEmptyStateView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);

            // Kick off helpder to oad actual Movie content from server.
            loadMovies();
        }
    }

    /**
     * Helper method to show progress bar and fetch Movie data from the server.
     */
    private void loadMovies() {
        // Show progress bar
        mProgressBar.setVisibility(View.VISIBLE);

        // Kick off task to load movies data
        new FetchMoviesTask().execute();
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent intent = new Intent(context, MovieDetailsActivity.class);

        // Include the movie that was clicked on for callee to use when loading data.
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movies_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Check for network availability when menu sorting options
         * are selected since they're dependent on a network connection.
         */
        if (!NetworkUtils.isNetworkUp(this)) {
            /* If no network, show the empty state and exit
             * the method (no point in trying to load data).
             */
            mEmptyStateView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            return false;
        } else {
            mEmptyStateView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        switch (item.getItemId()) {
            case R.id.sort_popular:
                // Valid option, show progress bar
                mProgressBar.setVisibility(View.VISIBLE);

                // Kick off task to load popular movies data
                new FetchMoviesTask().execute(POPULAR_MOVIES);
                break;
            case R.id.sort_top_rated:
                // Valid option, show progress bar
                mProgressBar.setVisibility(View.VISIBLE);

                // Kick off task to load top rated movies data
                new FetchMoviesTask().execute(TOP_RATED_MOVIES);
                break;
            default:
                // Invalid option (somehow), just exit method.
                return false;
        }
        return true;
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(String... params) {
            URL url = null;

            // If sort by popular/top rated was requested, build that into URL
            if (params.length == 1) {
                url = NetworkUtils.buildUrl(params[0]);
            } else {
                url = NetworkUtils.buildUrl(null);
            }

            String jsonResponse = "";
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }

            Movie[] movies = null;
            try {
                // We expect a variable-sized response, so store that initially in a List.
                List<Movie> moviesList = JsonUtils.getMoviesFromJson(jsonResponse);

                // Now we know the size the array should be, so convert the list to Movie array.
                movies = moviesList.toArray(new Movie[0]);
            } catch (JSONException jse) {
                jse.printStackTrace();
                return null;
            }

            return movies;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            // Hide progress bar
            mProgressBar.setVisibility(View.INVISIBLE);

            if (movies != null) {
                mMovieAdapter.setmMoviesData(movies);
            }
        }
    }
}