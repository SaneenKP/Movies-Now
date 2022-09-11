package com.entriappassignment.moviesnow.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.entriappassignment.moviesnow.Constants
import com.entriappassignment.moviesnow.paging.MoviePagingSource
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import javax.inject.Inject

class MovieRepository @Inject constructor(val moviesRetrofitClient: MoviesRetrofitClient) {

    fun getMovies() = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, maxSize = Constants.MAX_PAGE_COUNT),
        pagingSourceFactory = { MoviePagingSource(moviesRetrofitClient) }
    ).liveData
}