package com.entriappassignment.moviesnow.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.entriappassignment.moviesnow.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
data class MovieData(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id"                ) var id               : Int,
    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
//    @SerializedName("genre_ids"         ) var genreIds         : MutableList<Int> = arrayListOf(),
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @SerializedName("overview"          ) var overview         : String?        = null,
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @SerializedName("title"             ) var title            : String?        = null,
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @SerializedName("vote_average"      ) var voteAverage      : Float?           = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null
)