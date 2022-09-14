package com.entriappassignment.moviesnow.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsData(

    @SerializedName("id"                    ) var id                  : Int?                           = null,
    @SerializedName("adult"                 ) var adult               : Boolean?                       = null,
    @SerializedName("backdrop_path"         ) var backdropPath        : String?                        = null,
    @SerializedName("genres"                ) var genres              : ArrayList<Genres>              = arrayListOf(),
    @SerializedName("homepage"              ) var homepage            : String?                        = null,
    @SerializedName("original_language"     ) var originalLanguage    : String?                        = null,
    @SerializedName("overview"              ) var overview            : String?                        = null,
    @SerializedName("poster_path"           ) var posterPath          : String?                        = null,
    @SerializedName("release_date"          ) var releaseDate         : String?                        = null,
    @SerializedName("runtime"               ) var runtime             : Int?                           = null,
    @SerializedName("tagline"               ) var tagline             : String?                        = null,
    @SerializedName("title"                 ) var title               : String?                        = null,
    @SerializedName("vote_average"          ) var voteAverage         : Double?                        = null,
    @SerializedName("vote_count"            ) var voteCount           : Int?                           = null
)
