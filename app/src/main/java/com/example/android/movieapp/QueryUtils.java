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
import java.util.List;

import static com.example.android.movieapp.MovieFragment.LOG_TAG;

/**
 * Created by dnj on 1/20/17.
 */

public class QueryUtils {
    public static List<Movie> fetchMovieData(URL url) {
        String jsonString = null;

        if (url == null) {
            return null;
        }

        try {
            jsonString = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "HTTP REQUEST ERROR: " + e);
        }

        List<Movie> arrayList = parseJson(jsonString);
        return arrayList;
    }

    private static List<Movie> parseJson(String jsonString) {

        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        List<Movie> arraylist = new ArrayList<>();

        if (jsonString == null) {
            return null;
        }

        try {
            JSONObject root = new JSONObject(jsonString);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);

                // getters
                String title = element.getString("original_title");
                String plot = element.getString("overview");
                String release = element.getString("release_date");
                double rating = element.getDouble("vote_average");
                String poster = element.getString("poster_path");


                // set full URL for poster image
                String posterURLString = BASE_IMAGE_URL + poster;
                Log.e(LOG_TAG, "POSTER PATH " + posterURLString);

                // create new movie object
                Movie movie = new Movie(title, release, rating, plot, posterURLString);

                // add new movie to arraylist
                arraylist.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "PARSE Error: " + e);
        }

        return arraylist;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        if (url == null) {
            return null;
        }

        HttpURLConnection conn = null;
        InputStream inputStream;
        String jsonString = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
            conn.connect();
            int code = conn.getResponseCode();
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                jsonString = reader(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "CONNECTION ERROR: " + e);
        }

        return jsonString;
    }

    private static String reader(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line = buffReader.readLine();
            while (line != null) {
                builder.append(line);
                line = buffReader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "INPUT STREAM READER ERROR: " + e);
        }
        return builder.toString();
    }
}
