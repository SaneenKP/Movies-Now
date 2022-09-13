package com.entriappassignment.moviesnow.utils

class Constants {
    companion object{

        //Paging Constants
        const val MAX_PAGE_COUNT = 100 //maximum no of pages to be loaded to pagingData ,after this the pages would be dropped
        const val PAGE_SIZE = 20 //no of pages loaded at once

        //Room Constants
        const val DB_NAME = "MoviesNowDB"
        const val DATABASE_VERSION = 1
        const val MOVIE_TABLE_NAME = "Movie"
        const val PAGING_KEY_TABLE_NAME = "MoviePagingKeys"

        //Glide Constants
        const val GLIDE_IMAGE_SIZE = "w500"

        //Intent Extras
        const val MOVIE_ID = "movie_id"
    }
}