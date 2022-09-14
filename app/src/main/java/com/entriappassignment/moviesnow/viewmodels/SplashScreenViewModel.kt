package com.entriappassignment.moviesnow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entriappassignment.moviesnow.models.Configuration
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import com.entriappassignment.moviesnow.retrofit.ResponseWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SplashScreenViewModel : ViewModel() {

    private val moviesRetrofitClient : MoviesRetrofitClient = MoviesRetrofitClient()
    private var disposable : CompositeDisposable = CompositeDisposable()
    val configurationResponse : MutableLiveData<ResponseWrapper<Configuration>> = MutableLiveData()

    fun getConfiguration(){
        disposable.add(moviesRetrofitClient.getConfiguration()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                configurationResponse.value = ResponseWrapper.loading()
            }
            .subscribeWith(object : DisposableSingleObserver<Configuration>(){
                override fun onSuccess(t: Configuration) {
                   configurationResponse.value = ResponseWrapper.success(t)
                }

                override fun onError(e: Throwable) {
                    configurationResponse.value = e.message?.let { ResponseWrapper.error(it) }
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}