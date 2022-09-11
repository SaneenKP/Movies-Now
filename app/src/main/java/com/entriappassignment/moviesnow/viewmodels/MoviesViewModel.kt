package com.entriappassignment.moviesnow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.repository.MovieRepository
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient

class MoviesViewModel() : ViewModel() {

    private var movieList = MovieRepository(MoviesRetrofitClient()).getMovies().cachedIn(viewModelScope)

    fun getMovieList(): LiveData<PagingData<MovieData>>? {
        return movieList
    }
}
