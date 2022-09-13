package com.entriappassignment.moviesnow.paging

import android.graphics.Movie
import androidx.paging.*
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.entriappassignment.moviesnow.db.MovieDatabase
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.MoviePagingKeys
import com.entriappassignment.moviesnow.retrofit.MoviesRetrofitClient
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Utils
import java.io.IOException

@ExperimentalPagingApi
class RemoteMediator(
    val moviesRetrofitClient: MoviesRetrofitClient,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieData>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieData>
    ): MediatorResult {

        try {
            val pageKeyData = getKeyPageData(loadType , state)
            val page = when(pageKeyData){
                is MediatorResult.Success -> {

                    Utils.debug("mediator result success = $pageKeyData")
                    return pageKeyData
                }
                else -> {
                    Utils.debug("mediator result failed = $pageKeyData")
                    pageKeyData as Int
                }
            }
            Utils.debug("page we got = $page")
            val movieResponse = moviesRetrofitClient.getNowPlayingMovies(page)
            val movies = movieResponse.movies

            var totalPages = movieResponse.totalPages
            val endOfPaginationReached = (page == totalPages)

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    movieDatabase.movieDao().deleteMovie()
                    movieDatabase.moviePagingKeyDao().deleteAllPagingKeys()
                }

                val prevPage = if (page == 1) null else (page-1)
                val nextPage = if (endOfPaginationReached) null else (page+1)

                val keys = movies.map {
                    MoviePagingKeys(it.id , prevPage = prevPage , nextPage = nextPage)
                }

                movieDatabase.moviePagingKeyDao().addAllPagingKeys(keys)
                movieDatabase.movieDao().addMovies(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e : Exception){
            Utils.error("exception Error : ${e.message.toString()}")
            return MediatorResult.Error(e)
        }catch (ioException : IOException){
            Utils.error("IO Error : ${ioException.message.toString()}")
            return MediatorResult.Error(ioException)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieData>): Any {
        return when(loadType){

            LoadType.REFRESH -> {
                Utils.debug("Refresh called")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.APPEND -> {
                Utils.debug("Append called")
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextPage
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }

            LoadType.PREPEND -> {
                Utils.debug("Prepend Called")
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieData>): MoviePagingKeys?{
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> movieDatabase.moviePagingKeyDao().getMoviePagingKey(movie.id) }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieData>): MoviePagingKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> movieDatabase.moviePagingKeyDao().getMoviePagingKey(movie.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieData>): MoviePagingKeys? {
        return state.anchorPosition?.let {position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                movieDatabase.moviePagingKeyDao().getMoviePagingKey(movieId)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

}