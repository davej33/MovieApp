package com.example.android.movieapp;

import android.graphics.Bitmap;

/**
 * Created by dnj on 1/20/17.
 */

public class Movie {

    private String mTitle;
    private String mDateRelease;
    private double mRating;
    private String mPlot;
    private Bitmap mCover;

    public Movie(String t, String d, double r, String p, Bitmap c) {
        mTitle = t;
        mDateRelease = d;
        mRating = r;
        mPlot = p;
        mCover = c;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDateRelease() {
        return mDateRelease;
    }

    public double getmRating() {
        return mRating;
    }

    public String getmPlot() {
        return mPlot;
    }

    public Bitmap getmCover() {
        return mCover;
    }
}
