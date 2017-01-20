package com.example.android.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by dnj on 1/20/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cover_image, parent, false);
        }

        Movie currentMovie = getItem(position);

        ImageView image = (ImageView) listItemView.findViewById(R.id.cover_view);
        image.setImageBitmap(currentMovie.getmCover());

        return listItemView;
    }
}
