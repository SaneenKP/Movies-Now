package com.entriappassignment.moviesnow.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import java.lang.Exception

class MoviePagingSource(private val moviesRetrofitClient: MoviesRetrofitClient) :
    PagingSource<Int, MovieData>() {

    private val INITIAL_PAGE = 1

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val position = params.key ?:INITIAL_PAGE
            var response = moviesRetrofitClient.getNowPlayingMovies(position)
            LoadResult.Page(
                data = response.movies,
                prevKey = if (position == INITIAL_PAGE) null else position-1,
                nextKey = if (position == response.totalPages) null else position+1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}