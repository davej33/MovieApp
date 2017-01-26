package com.example.android.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.android.movieapp.MovieFragment.LOG_TAG;

/**
 * Created by dnj on 1/20/17.
 */

public class QueryUtils {
    public static ArrayList<Movie> fetchMovieData(URL url) {
        String jsonString = null;
        Log.e(LOG_TAG, "URL: " + url);
        if(url == null){
            return null;
        }
        try {
            jsonString = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Movie> arraylist = parseJson(jsonString);
        return arraylist;
    }

    private static ArrayList<Movie> parseJson(String jsonString) {
        final String MOVIE_TITLE = String.valueOf(R.string.title);
        final String MOVIE_PLOT = String.valueOf(R.string.plot);
        final String MOVIE_RELEASE_DATE = String.valueOf(R.string.release_date);
        final String MOVIE_POSTER = String.valueOf(R.string.poster);
        final String MOVIE_RATING = String.valueOf(R.string.rating);
        final String RESULT = String.valueOf(R.string.results);
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        ArrayList<Movie> arraylist = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(jsonString);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);

                String title = element.getString(MOVIE_TITLE);
                Log.e(LOG_TAG, "title: " + title);
                String plot = element.getString(MOVIE_PLOT);
                String release = element.getString(MOVIE_RELEASE_DATE);
                double rating = element.getDouble(MOVIE_RATING);
                String poster = element.getString("poster_path");
                String parsedPoster = parsePoster(poster);
                String posterURLString = BASE_IMAGE_URL + parsedPoster;

                Movie movie = new Movie(title,release,rating,plot,posterURLString);

                arraylist.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error: " + e);
        }


        return arraylist;
    }

    private static String parsePoster(String poster) {
        Log.e(LOG_TAG, "poster: " + poster);
        char[] array = poster.toCharArray();
        char[] array2 = Arrays.copyOfRange(array, 1, 6);
        String parsedPoster = String.valueOf(array2);
        return parsedPoster;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        if (url != null) {
            return null;
        }
        HttpURLConnection conn;
        InputStream inputStream;
        String jsonString = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                jsonString = reader(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    private static String reader(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line = buffReader.readLine();
            while(line != null){
                builder.append(line);
                line = buffReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
