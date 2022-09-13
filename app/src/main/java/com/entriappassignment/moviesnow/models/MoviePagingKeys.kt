package com.entriappassignment.moviesnow.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.entriappassignment.moviesnow.utils.Constants

@Entity(tableName = Constants.PAGING_KEY_TABLE_NAME)
data class MoviePagingKeys(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage : Int?,
    val nextPage : Int?
)

