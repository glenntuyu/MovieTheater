package com.example.movietheater.usecase

import androidx.paging.PagingData
import com.example.movietheater.data.MovieRepository
import com.example.movietheater.presentation.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun getTopRatedMovies(): Flow<PagingData<TopRatedMovieDataView>> {
        return movieRepository.getTopRatedMovies()
    }

    suspend fun getMovieDetail(id: Int): MovieDetailResult {
        return movieRepository.getMovieDetail(id)
    }
}