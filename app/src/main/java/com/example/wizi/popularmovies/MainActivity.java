package com.example.wizi.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
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

import com.example.wizi.popularmovies.utilities.QueryUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieAdapter movieAdapter;
    private RecyclerView mMoviesList;
    private ProgressBar mLoadingIndicator;
    private TextView noInternetTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = findViewById(R.id.rv_movies);
        noInternetTextView = findViewById(R.id.tv_error_no_internet);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mMoviesList.setLayoutManager(gridLayoutManager);
        mMoviesList.setHasFixedSize(true);

        List<Movie> movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movies);
        mMoviesList.setAdapter(movieAdapter);

        boolean internetConnected = checkInternetConnection();

        if (internetConnected) {
            loadMovieData();
        }
    }

    private void loadMovieData() {
        String sortedBy = QueryUtils.getSortingMethod();
        new FetchMoviesTask().execute(sortedBy);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String sortedBy = params[0];
            URL movieRequestUrl = QueryUtils.buildQueryUrl(sortedBy);

            try {
                String jsonMovieResponse = QueryUtils.getResponseFromHttpUrl(movieRequestUrl);

                return QueryUtils.parseJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movies != null) {
                movieAdapter.setMovieData(movies);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_top_rated) {
            QueryUtils.setSortingMethod("top_rated");
            checkInternetConnection();
            loadMovieData();
            return true;
        }

        if (id == R.id.action_popular) {
            QueryUtils.setSortingMethod("popular");
            checkInternetConnection();
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
            showMovies();
            return true;
        } else {
            showErrorNoInternet();
            return false;
        }
    }

    private void showErrorNoInternet() {
        mMoviesList.setVisibility(View.INVISIBLE);
        noInternetTextView.setVisibility(View.VISIBLE);
    }

    private void showMovies() {
        noInternetTextView.setVisibility(View.INVISIBLE);
        mMoviesList.setVisibility(View.VISIBLE);
    }

}


