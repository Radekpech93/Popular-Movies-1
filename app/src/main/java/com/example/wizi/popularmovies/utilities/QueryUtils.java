package com.example.wizi.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.wizi.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.wizi.popularmovies.BuildConfig.API_KEY;

public final class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private static final String MOVIE_PARAMETER = "movie";
    private static final String API_PARAMETER = "api_key";

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    private static final String MOVIE_RESULTS = "results";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POSTER = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_AVERAGE_VOTE = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";

    private static String sortedBy = POPULAR;


    public static String setSortingMethod(String sortBy){
        sortedBy = sortBy;
        return sortedBy;
    }

    public static String getSortingMethod(){
        return sortedBy;
    }

    public static URL buildQueryUrl(String sortedBy) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MOVIE_PARAMETER)
                .appendPath(sortedBy)
                .appendQueryParameter(API_PARAMETER, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI (QUERY) " + url);

        return url;
    }

    public static String buildPosterUrl(String posterUrl) {



        Uri.Builder builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(posterUrl);

        return builtUri.toString();
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<Movie> parseJson(String forecastJsonStr)
            throws JSONException {

        List<Movie> movies = new ArrayList<>();
        String title;
        String posterPath;
        String averageVote;
        String overview;
        String releaseDate;

        JSONObject rootJson;

        if(forecastJsonStr != null) {
            rootJson = new JSONObject(forecastJsonStr);
        }else{
            return null;
        }

        JSONArray movieArray = rootJson.getJSONArray(MOVIE_RESULTS);

        for(int i = 0; i < movieArray.length(); i++){

            JSONObject movieDetails = movieArray.getJSONObject(i);


            title = movieDetails.getString(MOVIE_TITLE);
            posterPath = movieDetails.getString(MOVIE_POSTER);
            overview = movieDetails.getString(MOVIE_OVERVIEW);
            averageVote = movieDetails.getString(MOVIE_AVERAGE_VOTE);
            releaseDate = movieDetails.getString(MOVIE_RELEASE_DATE);

            movies.add(new Movie(title, posterPath, overview, averageVote, releaseDate)) ;
        }

        return movies;

    }


}
