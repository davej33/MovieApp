package com.example.android.movieapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieAsyncTaskFragment extends AsyncTaskLoader {

    private URL mUrl;

    public MovieAsyncTaskFragment(Context context, URL url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        ArrayList<Movie> arrayList = QueryUtils.fetchMovieData(mUrl);
        return arrayList;
    }
}
