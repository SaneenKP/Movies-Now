package com.entriappassignment.moviesnow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import com.entriappassignment.moviesnow.retrofit.ResponseWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel () : ViewModel() {

    private val moviesRetrofitClient : MoviesRetrofitClient = MoviesRetrofitClient()
    private var disposable : CompositeDisposable = CompositeDisposable()
    val movieDetailsResponse : MutableLiveData<ResponseWrapper<MovieDetailsData>> = MutableLiveData()

    fun getMovieDetails(movieId: Int){

        //Uses Rx java to fetch the movie details and updates the status on the response Wrapper.
        disposable.add(moviesRetrofitClient.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                movieDetailsResponse.value = ResponseWrapper.loading()
            }
            .subscribeWith(object : DisposableSingleObserver<MovieDetailsData>(){
                override fun onSuccess(t: MovieDetailsData) {
                    movieDetailsResponse.value = ResponseWrapper.success(t)
                }
                override fun onError(e: Throwable) {
                    movieDetailsResponse.value = e.message?.let { ResponseWrapper.error(it) }
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}