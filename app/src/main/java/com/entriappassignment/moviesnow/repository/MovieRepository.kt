package com.entriappassignment.moviesnow.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.entriappassignment.moviesnow.paging.MoviePagingSource
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import javax.inject.Inject

class MovieRepository @Inject constructor(val moviesRetrofitClient: MoviesRetrofitClient) {

    fun getMovies() = Pager(
        config = PagingConfig(pageSize = 20 , maxSize = 100),
        pagingSourceFactory = { MoviePagingSource(moviesRetrofitClient) }
    ).liveData
}