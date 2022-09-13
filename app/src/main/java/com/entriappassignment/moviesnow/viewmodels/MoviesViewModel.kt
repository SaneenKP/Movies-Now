package com.entriappassignment.moviesnow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.entriappassignment.moviesnow.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    var movieList =  movieRepository.getMovies().cachedIn(viewModelScope)
}
