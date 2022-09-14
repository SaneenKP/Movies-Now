package com.entriappassignment.moviesnow.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.entriappassignment.moviesnow.models.NowPlayingMovieResult
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import com.entriappassignment.moviesnow.utils.Constants
import java.lang.Exception

class MoviePagingSource(private val moviesRetrofitClient: MoviesRetrofitClient) :
    PagingSource<Int, NowPlayingMovieResult>() {

    private val INITIAL_PAGE = Constants.INITIAL_PAGE

    override fun getRefreshKey(state: PagingState<Int, NowPlayingMovieResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingMovieResult> {
        return try {
            val position = params.key ?:INITIAL_PAGE
            var response = moviesRetrofitClient.getNowPlayingMovies(position)
            LoadResult.Page(
                data = response.movieResults,
                prevKey = if (position == INITIAL_PAGE) null else position-1,
                nextKey = if (position == response.totalPages) null else position+1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}