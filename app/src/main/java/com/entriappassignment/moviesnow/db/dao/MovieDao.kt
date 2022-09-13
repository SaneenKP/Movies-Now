package com.entriappassignment.moviesnow.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entriappassignment.moviesnow.models.NowPlayingMovieResult

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies() : PagingSource<Int, NowPlayingMovieResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies : List<NowPlayingMovieResult>)

    @Query("DELETE FROM movie")
    suspend fun deleteMovie()
}