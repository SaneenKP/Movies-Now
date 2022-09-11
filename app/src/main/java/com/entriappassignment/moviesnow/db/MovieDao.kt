package com.entriappassignment.moviesnow.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entriappassignment.moviesnow.models.MovieData

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies() : PagingSource<Int, MovieData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies : List<MovieData>)

    @Query("DELETE FROM movie")
    suspend fun deleteMovie()
}