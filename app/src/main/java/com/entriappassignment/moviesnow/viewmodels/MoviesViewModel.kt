package com.entriappassignment.moviesnow.viewmodels

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entriappassignment.moviesnow.Utils
import com.entriappassignment.moviesnow.helpers.NowPlayingMoviesResponse
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import com.entriappassignment.moviesnow.retrofit.MoviesApiServiceRx
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer


class MoviesViewModel : ViewModel(){

    private val moviesRetrofitClient = MoviesRetrofitClient()
    private val disposable = CompositeDisposable()
    private val response = MutableLiveData<NowPlayingMoviesResponse>()

    fun refresh(){
        fetchNowPlayingMovies()
    }

    fun getResponse() : MutableLiveData<NowPlayingMoviesResponse>{
        return response
    }

    private fun fetchNowPlayingMovies(){

        disposable.add(
            moviesRetrofitClient.getNowPlayingMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    getResponse().value = NowPlayingMoviesResponse().loading()
                }
                .subscribe(
                    { nowPlayingMovie ->
                        getResponse().value = NowPlayingMoviesResponse().success(nowPlayingMovie)
                    },
                    { error ->
                        getResponse().value = NowPlayingMoviesResponse().error(error)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}



