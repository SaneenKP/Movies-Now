package com.entriappassignment.moviesnow.viewmodels

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.repository.MovieRepository
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    var movieList =  movieRepository.getMovies().cachedIn(viewModelScope)
}
