package com.example.android.moviechef;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.moviechef.utilities.JsonUtils;
import com.example.android.moviechef.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieGridActivity extends AppCompatActivity
                            implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    private MovieAdapter mMovieAdapter;

    private static final int SPAN_COUNT = 2;

    private static final String POPULAR_MOVIES = "popular";
    private static final String TOP_RATED_MOVIES = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_grid);
        mGridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMovieAdapter = new MovieAdapter(MovieGridActivity.this, this);
        mRecyclerView.setAdapter(mMovieAdapter);
        loadMovies();
    }

    private void loadMovies() {
        new FetchMoviesTask().execute();
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        // TODO: do stuff when movie clicked ;)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movies_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_popular:
                new FetchMoviesTask().execute(POPULAR_MOVIES);
                break;
            case R.id.sort_top_rated:
                new FetchMoviesTask().execute(TOP_RATED_MOVIES);
                break;
            default:
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

            Log.v(LOG_TAG, "JSON response: " + jsonResponse);

            Movie[] movies = null;
            try {
                List<Movie> moviesList = JsonUtils.getMoviesFromJson(MovieGridActivity.this, jsonResponse);
                movies = moviesList.toArray(new Movie[0]);
            } catch (JSONException jse) {
                jse.printStackTrace();
                return null;
            }

            //Log.v(LOG_TAG, "First movie: " + movies.length);
            //Log.v(LOG_TAG, "First movie: " + movies[0].toString());

            return movies;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter.setmMoviesData(movies);
            }
        }
    }
}