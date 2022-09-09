package com.entriappassignment.moviesnow.retrofit

import android.util.Log
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


class MoviesRetrofitClient() {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val API_KEY = "9a976526fce8c29aaa35eb4a1e654d3c"

    private var moviesApiServiceRx : MoviesApiServiceRx
    private var gsonConverterFactory : GsonConverterFactory
    private var requestInterceptor : Interceptor

    init {
        moviesApiServiceRx = getMoviesApiServiceRx()
        gsonConverterFactory = getGsonConverterFactory()
        requestInterceptor = getInterceptor()
    }

    private fun getInterceptor() : Interceptor{
        if (requestInterceptor == null){
           requestInterceptor = Interceptor{

               val url = it.request()
                   .url
                   .newBuilder()
                   .addQueryParameter("api_key" , API_KEY)
                   .build()

               val request = it.request()
                   .newBuilder()
                   .url(url)
                   .build()

               return@Interceptor it.proceed(request)
           }
        }
        return requestInterceptor
    }

    private fun getGsonConverterFactory() : GsonConverterFactory{
        if (gsonConverterFactory == null){
            gsonConverterFactory = GsonConverterFactory.create();
        }
        return gsonConverterFactory
    }

    private fun getOkHttpClient() : OkHttpClient{

        var httLog : HttpLoggingInterceptor = HttpLoggingInterceptor()
        httLog.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
           .addInterceptor(getInterceptor()).addInterceptor(httLog)
           .connectTimeout(60 , TimeUnit.SECONDS)
           .build()

        return okHttpClient
    }

    private fun getMoviesApiServiceRx() : MoviesApiServiceRx{

        if (moviesApiServiceRx == null){

            var retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(getGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            moviesApiServiceRx = retrofit.create(MoviesApiServiceRx::class.java)
        }

        return moviesApiServiceRx
    }

    fun getNowPlayingMovies(): Single<NowPlayingMoviesData> {
        return getMoviesApiServiceRx().
        getNowPlayingMovies()
    }

}