package com.entriappassignment.moviesnow.retrofit

import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiServiceRx {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") pageNo : Int): NowPlayingMoviesData
}