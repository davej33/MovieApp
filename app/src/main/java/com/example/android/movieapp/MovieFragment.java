package com.example.android.movieapp;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    // constants
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    // vars
    private MovieAdapter mAdapter;
    private TextView mEmptyTextView;
    private static final int MOVIE_LOADER = 0;
    public static Movie mMovie;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);


        final GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        mAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMovie = (Movie) gridView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        // get network connection
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            setupSharedPreferences();
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER, null, this);
        } else {
            mEmptyTextView.setText(R.string.empty_text); // fix
        }


        return rootView;
    }

    private void setupSharedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
        pref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortValue = pref.getString(getString(R.string.pref_sort_key), "");

        // build URI
        final String BASE_URI = "https://api.themoviedb.org/3/discover/movie?";

        Uri baseUri = Uri.parse(BASE_URI);
        Uri buildtUri = baseUri.buildUpon()
                .appendQueryParameter(getString(R.string.pref_sort_key), sortValue)
                .appendQueryParameter(getString(R.string.api_code_key), BuildConfig.MOVIE_API_VALUE)
                .build();
        URL url = null;
        try {
            url = new URL(buildtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new MovieAsyncTaskFragment(getActivity(), url);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
