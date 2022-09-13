package com.entriappassignment.moviesnow.models

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesData(
    @SerializedName("dates"         ) var dates        : Dates?             = Dates(),
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var movieResults      : MutableList<NowPlayingMovieResult> = mutableListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
)
