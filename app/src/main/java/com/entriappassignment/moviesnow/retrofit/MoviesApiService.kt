package com.entriappassignment.moviesnow.retrofit

import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") pageNo : Int): NowPlayingMoviesData
}