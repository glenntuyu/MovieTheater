package com.example.movietheater.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietheater.api.MovieService
import com.example.movietheater.db.RemoteKeys
import com.example.movietheater.db.TopRatedMovieDatabase
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.presentation.model.convertToTopRatedMovieList
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val service: MovieService,
    private val topRatedMovieDatabase: TopRatedMovieDatabase
) : RemoteMediator<Int, TopRatedMovieDataView>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TopRatedMovieDataView>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getTopRatedMovies(page, MovieService.API_KEY)

            val movies = apiResponse.movies.convertToTopRatedMovieList()
            val endOfPaginationReached = movies.isEmpty()
            topRatedMovieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    topRatedMovieDatabase.remoteKeysDao().clearRemoteKeys()
                    topRatedMovieDatabase.moviesDao().clearMovies()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeys(movieId = it.movieId, prevKey = prevKey, nextKey = nextKey)
                }
                topRatedMovieDatabase.remoteKeysDao().insertAll(keys)
                topRatedMovieDatabase.moviesDao().insertAll(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TopRatedMovieDataView>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                topRatedMovieDatabase.remoteKeysDao().remoteKeysMovieId(movie.movieId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TopRatedMovieDataView>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                topRatedMovieDatabase.remoteKeysDao().remoteKeysMovieId(movie.movieId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopRatedMovieDataView>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                topRatedMovieDatabase.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }
}
