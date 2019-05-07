package com.home.tmdbretrofitapp.service;

import com.home.tmdbretrofitapp.model.MovieDBResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key")String api_key);
    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMoviesWithPaging(@Query("api_key")String api_key,@Query("page")long pageNumber);
    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithPagingRx(@Query("api_key")String api_key, @Query("page")long pageNumber);
}
