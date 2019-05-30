package com.home.tmdbretrofitapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.home.tmdbretrofitapp.R;
import com.home.tmdbretrofitapp.service.MovieDataService;
import com.home.tmdbretrofitapp.service.RetrofitInstance;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//***AppRepository Alternative for android PagingLibrary***//
public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {
    private MovieDataService movieDataService;
    private Application application;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Observable<MovieDBResponse> movieDBResponseObservable;
    private ArrayList<Movie> movies;


    public MovieDataSource(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        movieDataService = RetrofitInstance.getService();
        movieDBResponseObservable = movieDataService.getPopularMoviesWithPagingRx(application.getApplicationContext().getString(R.string.api_key), 1);
        movies = new ArrayList<>();
        compositeDisposable.add(movieDBResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                //add more control with flat map oprator get response as obserbable
                .flatMap(new Function<MovieDBResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                        return Observable.fromArray(movieDBResponse.getResults().toArray(new Movie[0]));
                    }
                })

                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        movies.add(movie);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        callback.onResult(movies, null, (long) 2);
                    }
                }));


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        movieDataService = RetrofitInstance.getService();

        movieDBResponseObservable = movieDataService.getPopularMoviesWithPagingRx(application.getApplicationContext().getString(R.string.api_key), params.key);
        movies = new ArrayList<>();
        compositeDisposable.add(movieDBResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                //add more control with flat map oprator get response as obserbable
                .flatMap(new Function<MovieDBResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                        return Observable.fromArray(movieDBResponse.getResults().toArray(new Movie[0]));
                    }
                })

                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        movies.add(movie);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        callback.onResult(movies, params.key + 1);
                    }
                }));

    }

    public void clear(){


        compositeDisposable.clear();
    }
}
