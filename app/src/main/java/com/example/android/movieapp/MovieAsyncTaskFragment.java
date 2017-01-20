package com.example.android.movieapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieAsyncTaskFragment extends AsyncTaskLoader {

    private String mUrl;

    public MovieAsyncTaskFragment(Context context, String url) {
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
        return null;
    }
}
