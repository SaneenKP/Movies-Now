package com.entriappassignment.moviesnow.retrofit

import com.entriappassignment.moviesnow.models.Configuration
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") pageNo : Int): NowPlayingMoviesData

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId : Int): Single<MovieDetailsData>

    @GET("configuration")
    fun getConfiguration() : Single<Configuration>
}