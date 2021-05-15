package com.example.movietheater.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movietheater.api.MovieService
import com.example.movietheater.db.TopRatedMovieDatabase
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.presentation.model.convertToMovieDetailDataView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDatabase: TopRatedMovieDatabase,
): MovieRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    override fun getTopRatedMovies(): Flow<PagingData<TopRatedMovieDataView>> {
        val pagingSourceFactory = { movieDatabase.moviesDao().getMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(movieService, movieDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getMovieDetail(id: Int): MovieDetailResult {
        val response = movieService.getMovieDetail(id, MovieService.API_KEY)

        return if (response.isSuccessful) {
            MovieDetailResult.Success(response.body()?.convertToMovieDetailDataView())
        } else {
            MovieDetailResult.Error(response.message())
        }
    }
}