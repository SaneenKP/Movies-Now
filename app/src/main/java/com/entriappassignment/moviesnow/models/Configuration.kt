package com.entriappassignment.moviesnow.models

import com.google.gson.annotations.SerializedName

data class Configuration(
    @SerializedName("base_url")
    var imageBaseUrl : String? = null
)
