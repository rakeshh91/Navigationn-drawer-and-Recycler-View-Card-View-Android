package com.rakesh_ap2016.hw5_rakeshne;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Movie_Fragement_ViewPager extends Fragment {


    private static final String ARG_MOVIE_NUMBER = "movie_number";
    android.support.v7.widget.ShareActionProvider shareActionProvider;

    private static HashMap<String,?> movieDataSelected;
    public static Movie_Fragement_ViewPager newInstance(HashMap<String,?> movieData) {
        // Required empty public constructor
        Movie_Fragement_ViewPager movieFragementViewPager = new Movie_Fragement_ViewPager();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_NUMBER, movieData);
        movieFragementViewPager.setArguments(args);
        movieDataSelected = movieData;
        return movieFragementViewPager;
    }

    public Movie_Fragement_ViewPager(){
        movieDataSelected = new HashMap<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie__fragement__view_pager, container, false);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments()!=null){
            movieDataSelected = (HashMap<String,?>)getArguments().getSerializable(ARG_MOVIE_NUMBER);
        }
        ImageView movieImageView = (ImageView)rootView.findViewById(R.id.moviesImage);
        movieImageView.setImageResource((Integer) movieDataSelected.get("image"));

        TextView movieName = (TextView)rootView.findViewById(R.id.movieName);
        movieName.setText((String) movieDataSelected.get("name"));

        TextView yearTextView = (TextView)rootView.findViewById(R.id.yearTextView);
        yearTextView.setText("( "+movieDataSelected.get("year")+" )");

        TextView duration = (TextView)rootView.findViewById(R.id.durationTextView);
        duration.setText((String)movieDataSelected.get("length"));

        TextView directorTextView = (TextView)rootView.findViewById(R.id.directorTextView);
        TextView directorListTextView = (TextView)rootView.findViewById(R.id.directorListTextView);
        directorListTextView.setText((String)movieDataSelected.get("director"));

        TextView castTextView = (TextView)rootView.findViewById(R.id.castTextView);
        TextView castListTextView = (TextView)rootView.findViewById(R.id.castListTextView);
        castListTextView.setText((String)movieDataSelected.get("stars"));

        RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.ratingBarStars);
        TextView ratingTextView = (TextView)rootView.findViewById(R.id.movieRatingTextView);
        Double rating = (Double)movieDataSelected.get("rating");
        ratingTextView.setText(""+rating);

        TextView movieDescriptionTextView = (TextView)rootView.findViewById(R.id.movieDescriptionTextView);
        movieDescriptionTextView.setText((String)movieDataSelected.get("description"));


        float ratingValue = rating.floatValue();
        ratingBar.setRating(ratingValue / 2);
        LayerDrawable stars = (LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#536DFE"), PorterDuff.Mode.SRC_ATOP);
        return rootView ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_MOVIE_NUMBER,movieDataSelected);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.shareData);
        Uri pictureUri = Uri.parse("(ImageView)movieDataSelected.get(\"image\")");
        shareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movieDataSelected.get("name"));
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
