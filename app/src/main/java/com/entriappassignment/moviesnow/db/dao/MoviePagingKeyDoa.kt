package com.entriappassignment.moviesnow.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entriappassignment.moviesnow.models.MoviePagingKeys

@Dao
interface MoviePagingKeyDoa {

    @Query("SELECT * FROM MoviePagingKeys WHERE id =:id ")
    suspend fun getMoviePagingKey(id : Int) : MoviePagingKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPagingKeys(keys : List<MoviePagingKeys>)

    @Query("DELETE FROM MoviePagingKeys")
    suspend fun deleteAllPagingKeys()
}