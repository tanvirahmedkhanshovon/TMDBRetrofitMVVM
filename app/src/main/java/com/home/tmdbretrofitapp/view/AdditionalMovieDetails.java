package com.home.tmdbretrofitapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.home.tmdbretrofitapp.R;

import com.home.tmdbretrofitapp.databinding.MovieMainBinding;
import com.home.tmdbretrofitapp.model.Movie;

public class AdditionalMovieDetails extends AppCompatActivity {

    private Movie movie;
    //private ImageView imageView;
    private String image;
    MovieMainBinding movieMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieMainBinding = DataBindingUtil.setContentView(this,R.layout.movie_main);
        Intent intent = getIntent();
        if(intent.hasExtra("movie")){

          movie = intent.getParcelableExtra("movie");

getSupportActionBar().setTitle(movie.getTitle());
            movieMainBinding.setMovie(movie);

        }
    }

}
