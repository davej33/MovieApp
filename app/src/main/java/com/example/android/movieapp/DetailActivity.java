package com.example.android.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.example.android.movieapp.MovieFragment.LOG_TAG;
import static com.example.android.movieapp.MovieFragment.mMovie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView image = (ImageView) findViewById(R.id.details_image_view);

        // imported picasso library for images
        Picasso.with(this).load(mMovie.getmCover())
                .error(R.drawable.alert)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(646, 970)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(image);


        TextView title = (TextView) findViewById(R.id.movie_title);
        title.setText(mMovie.getmTitle());

        TextView release = (TextView) findViewById(R.id.movie_release_date);
        release.setText(mMovie.getmDateRelease());

        TextView plot = (TextView) findViewById(R.id.plot);
        plot.setText(mMovie.getmPlot());
        plot.setMovementMethod(new ScrollingMovementMethod());

        RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setRating((MovieFragment.mMovie.getmRating()/2));

        Log.e(LOG_TAG, "RATING: "+MovieFragment.mMovie.getmRating());

    }
}