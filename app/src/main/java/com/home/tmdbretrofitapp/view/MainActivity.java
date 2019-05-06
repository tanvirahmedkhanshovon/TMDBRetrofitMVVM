package com.home.tmdbretrofitapp.view;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.tmdbretrofitapp.R;
import com.home.tmdbretrofitapp.adapter.MovieAdapter;
import com.home.tmdbretrofitapp.databinding.ActivityMainBinding;
import com.home.tmdbretrofitapp.model.Movie;
import com.home.tmdbretrofitapp.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private PagedList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        getPopularMovies();
        swipeRefreshLayout = activityMainBinding.swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });
    }

    private void getPopularMovies() {


        mainActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> moviedFomLiveData) {

                movies = moviedFomLiveData;
                setUpRecyclerView();
            }
        });


    }

    private void setUpRecyclerView() {

        recyclerView = activityMainBinding.rvMovies;
        movieAdapter = new MovieAdapter(this);
        movieAdapter.submitList(movies);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        } else {

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }
}
