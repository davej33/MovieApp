package com.example.android.movieapp;

/**
 * Created by dnj on 1/20/17.
 */

public class Movie {

    private String mTitle;
    private String mDateRelease;
    private float mRating;
    private String mPlot;
    private String mCover;


    public Movie(String t, String d, float r, String plot, String c) {
        mTitle = t;
        mDateRelease = d;
        mRating = r;
        mPlot = plot;
        mCover = c;

    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDateRelease() {
        return mDateRelease;
    }

    public float getmRating() {
        return mRating;
    }

    public String getmPlot() {
        return mPlot;
    }

    public String getmCover() {
        return mCover;
    }

}
