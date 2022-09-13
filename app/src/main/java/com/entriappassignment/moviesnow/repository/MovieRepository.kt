package com.entriappassignment.moviesnow.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.entriappassignment.moviesnow.db.MovieDatabase
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.paging.RemoteMediator
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepository @Inject constructor(
    val moviesRetrofitClient: MoviesRetrofitClient,
    val movieDatabase: MovieDatabase) {

    fun getMovies() = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, maxSize = Constants.MAX_PAGE_COUNT),
        remoteMediator = RemoteMediator(moviesRetrofitClient , movieDatabase)){
        movieDatabase.movieDao().getMovies()
    }.liveData
}