package com.example.android.moviechef;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MovieGridActivity extends AppCompatActivity
                            implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    private MovieAdapter mMovieAdapter;

    private static final int SPAN_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_grid);
        mGridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMovieAdapter = new MovieAdapter(this);
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

    private class FetchMoviesTask extends AsyncTask<Movie, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(Movie... params) {
            return new Movie[0];
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter.setmMoviesData(movies);
            }
        }
    }
}
