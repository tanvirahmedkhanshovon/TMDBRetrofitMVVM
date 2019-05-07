package com.home.tmdbretrofitapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.home.tmdbretrofitapp.model.Movie;
import com.home.tmdbretrofitapp.model.MovieDataSource;
import com.home.tmdbretrofitapp.model.MovieDataSourceFactory;
import com.home.tmdbretrofitapp.service.MovieDataService;
import com.home.tmdbretrofitapp.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);


        MovieDataService movieDataService = RetrofitInstance.getService();
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(movieDataService, application);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        executor = Executors.newFixedThreadPool(5);
        moviesPagedList = (new LivePagedListBuilder<Long, Movie>(movieDataSourceFactory, config))
                .setFetchExecutor(executor)
                .build();

    }


    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return moviesPagedList;
    }

//    public LiveData<List<Movie>> getAllMovies() {
//
//
//        return movieRepository.getMutableLiveData();
//    }
}
