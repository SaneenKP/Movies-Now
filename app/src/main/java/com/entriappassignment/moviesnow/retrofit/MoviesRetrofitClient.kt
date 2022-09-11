package com.entriappassignment.moviesnow.retrofit

import com.entriappassignment.moviesnow.BuildConfig.API_KEY
import com.entriappassignment.moviesnow.BuildConfig.BASE_URL
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit


class MoviesRetrofitClient() {

    private fun getInterceptor() : Interceptor{
       val requestInterceptor = Interceptor{

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
        return requestInterceptor
    }

    private fun getGsonConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
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

    private fun getMoviesApiServiceRx() : MoviesApiService{
        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(getGsonConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return  retrofit.create(MoviesApiService::class.java)
    }

    suspend fun getNowPlayingMovies(pageNo : Int): NowPlayingMoviesData {
        return getMoviesApiServiceRx().getNowPlayingMovies(pageNo)
    }

}