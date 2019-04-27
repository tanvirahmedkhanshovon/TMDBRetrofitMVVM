package com.home.tmdbretrofitapp.model;

import android.app.Application;


import androidx.lifecycle.MutableLiveData;

import com.home.tmdbretrofitapp.R;
import com.home.tmdbretrofitapp.service.MovieDataService;
import com.home.tmdbretrofitapp.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository

{

    private ArrayList<Movie> movieArrayList= new ArrayList<>();
    private MutableLiveData<List<Movie>>  mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {

        MovieDataService movieDataService = RetrofitInstance.getService();
        Call<MovieDBResponse> movies = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        movies.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();

                if(movieDBResponse!=null && movieDBResponse.getResults()!=null){

                    movieArrayList =(ArrayList<Movie>)movieDBResponse.getResults();
                    mutableLiveData.setValue(movieArrayList);

                   // setUpRecyclerView();



                } }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

              //  Log.i(TAG,t.getMessage());

            }
        });
        return mutableLiveData;
    }


}
