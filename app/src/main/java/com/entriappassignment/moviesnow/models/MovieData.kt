package com.entriappassignment.moviesnow.models

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("id")
    var movieId: Int,

    @SerializedName("original_language")
    var language: String,

    @SerializedName("original_title")
    var title: String,

    @SerializedName("overview")
    var description: String,

    @SerializedName("popularity")
    var popularity: Float,

    @SerializedName("poster_path")
    var poster: String,

    @SerializedName("release_date")
    var release: String,

    @SerializedName("revenue")
    var revenue: Long,

    @SerializedName("runtime")
    var runtime: Int,

    @SerializedName("vote_average")
    var rating: Float,
    )