package com.entriappassignment.moviesnow.retrofit

import com.entriappassignment.moviesnow.BuildConfig.API_KEY
import com.entriappassignment.moviesnow.BuildConfig.BASE_URL
import com.entriappassignment.moviesnow.models.Configuration
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MoviesRetrofitClient @Inject constructor() {

    @Singleton
    @Provides
    fun getInterceptor() : Interceptor{
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

    @Singleton
    @Provides
    fun getGsonConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun getOkHttpClient() : OkHttpClient{

        /***
         * Only use the logging interceptor to log , Do not use it in production.
         * This can cause a serious security issue and google play might reject it.
         */
//        var httLog : HttpLoggingInterceptor = HttpLoggingInterceptor()
//        httLog.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
           .addInterceptor(getInterceptor())
//            .addInterceptor(httLog)
           .connectTimeout(60 , TimeUnit.SECONDS)
           .build()

        return okHttpClient
    }

    @Singleton
    @Provides
    fun getMoviesApiServiceRx() : MoviesApiService{
        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(getGsonConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return  retrofit.create(MoviesApiService::class.java)
    }

    @Singleton
    @Provides
    suspend fun getNowPlayingMovies(pageNo : Int): NowPlayingMoviesData {
        return getMoviesApiServiceRx().getNowPlayingMovies(pageNo)
    }

    @Singleton
    @Provides
    fun getMovieDetails(id : Int): Single<MovieDetailsData> {
        return getMoviesApiServiceRx().getMovieDetails(id)
    }

    @Singleton
    @Provides
    fun getConfiguration(): Single<Configuration> {
        return getMoviesApiServiceRx().getConfiguration()
    }


}