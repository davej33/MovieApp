package com.example.android.movieapp;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    // constants
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    // vars
    private MovieAdapter mAdapter;
    private ProgressBar mProgBar;
    private String mUrl;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        return rootView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
