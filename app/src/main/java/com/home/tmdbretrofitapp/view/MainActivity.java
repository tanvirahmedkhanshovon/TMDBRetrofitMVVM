package com.home.tmdbretrofitapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.home.tmdbretrofitapp.R;
import com.home.tmdbretrofitapp.adapter.MovieAdapter;
import com.home.tmdbretrofitapp.databinding.ActivityMainBinding;
import com.home.tmdbretrofitapp.model.Movie;
import com.home.tmdbretrofitapp.model.MovieDBResponse;
import com.home.tmdbretrofitapp.service.MovieDataService;
import com.home.tmdbretrofitapp.service.RetrofitInstance;
import com.home.tmdbretrofitapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Movie> movieArrayList ;
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
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);



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


        mainActivityViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieArrayList =(ArrayList<Movie>)movies;
                setUpRecyclerView();
            }
        });






    }

    private void setUpRecyclerView() {

recyclerView = activityMainBinding.rvMovies;
movieAdapter = new MovieAdapter(MainActivity.this,movieArrayList);

if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
}
else {

    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
}

recyclerView.setItemAnimator(new DefaultItemAnimator());

recyclerView.setAdapter(movieAdapter);
movieAdapter.notifyDataSetChanged();


    }
}
