package com.entriappassignment.moviesnow.helpers

import com.entriappassignment.moviesnow.Constants.Companion.Status
import com.entriappassignment.moviesnow.Constants.Companion.Status.LOADING
import com.entriappassignment.moviesnow.Constants.Companion.Status.ERROR
import com.entriappassignment.moviesnow.Constants.Companion.Status.SUCCESS
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData

class NowPlayingMoviesResponse(
) {

    var status : Status? = null
    var data : NowPlayingMoviesData? = null
    var error : Throwable? = null

    fun loading() : NowPlayingMoviesResponse {
        this.status = LOADING
        this.data = null
        this.error = null
        return this
    }

    fun success(data : NowPlayingMoviesData): NowPlayingMoviesResponse{
        this.status = SUCCESS
        this.data = data
        this.error = null
        return this
    }

    fun error(error : Throwable): NowPlayingMoviesResponse {
        this.status = ERROR
        this.data = null
        this.error = error
        return this
    }
}