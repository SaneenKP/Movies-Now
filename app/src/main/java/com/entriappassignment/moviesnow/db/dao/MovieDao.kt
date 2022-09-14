package com.entriappassignment.moviesnow.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.utils.Constants

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Constants.MOVIE_TABLE_NAME}")
    fun getMovies() : PagingSource<Int, MovieData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies : List<MovieData>)

    @Query("DELETE FROM ${Constants.MOVIE_TABLE_NAME}")
    suspend fun deleteMovie()
}