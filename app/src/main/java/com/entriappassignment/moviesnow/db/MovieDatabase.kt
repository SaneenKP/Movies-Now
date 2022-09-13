package com.entriappassignment.moviesnow.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.db.dao.MovieDao
import com.entriappassignment.moviesnow.db.dao.MoviePagingKeyDoa
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.MoviePagingKeys

@Database(entities = [MovieData::class , MoviePagingKeys::class], version = Constants.DATABASE_VERSION)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun moviePagingKeyDao() : MoviePagingKeyDoa
}