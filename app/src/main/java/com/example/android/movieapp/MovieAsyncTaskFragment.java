package com.example.android.movieapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.net.URL;
import java.util.List;

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
    public List<Movie> loadInBackground() {
        List<Movie> arrayList = null;
        arrayList = QueryUtils.fetchMovieData(mUrl);
        return arrayList;
    }
}
