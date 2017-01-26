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
import static com.example.android.movieapp.R.string.poster;

/**
 * Created by dnj on 1/20/17.
 */

public class QueryUtils {
    public static List<Movie> fetchMovieData(URL url)  {
        String jsonString = null;
        Log.e(LOG_TAG, "URL: " + url);

        if(url == null){
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

        if(jsonString == null){
            return null;
        }
        final String MOVIE_TITLE = String.valueOf(R.string.title);
        final String MOVIE_PLOT = String.valueOf(R.string.plot);
        final String MOVIE_RELEASE_DATE = String.valueOf(R.string.release_date);
        final String MOVIE_POSTER = String.valueOf(poster);
        final String MOVIE_RATING = String.valueOf(R.string.rating);
        final String RESULT = String.valueOf(R.string.results);
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        List<Movie> arraylist = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(jsonString);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);



                String title = element.getString("title");
                String plot = element.getString("overview");
                String release = element.getString("release_date");
                double rating = element.getDouble("vote_average");
                String poster = element.getString("poster_path");
//                String parsedPoster = parsePoster(poster);
                String posterURLString = BASE_IMAGE_URL + poster;
                Log.e(LOG_TAG, "poster url string: " + posterURLString);

                Movie movie = new Movie(title,release,rating,plot,posterURLString);

                arraylist.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "PARSE Error: " + e);
        }


        return arraylist;
    }

//    private static String parsePoster(String poster) {
//        char[] array = poster.toCharArray();
//        char[] array2 = Arrays.copyOfRange(array, 1, array.length);
//        String parsedPoster = String.valueOf(array2);
//        Log.e(LOG_TAG, "Parse Poster: " + parsedPoster);
//        return parsedPoster;
//    }

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
            Log.e(LOG_TAG, "RESPONSE CODE: "+ code);
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
            while(line != null){
                builder.append(line);
                line = buffReader.readLine();
            }
        } catch (IOException e) {
           Log.e(LOG_TAG, "INPUT STREAM READER ERROR: " + e);
        }
        return builder.toString();
    }
}
