package com.example.movietheater.usecase

import com.example.movietheater.data.MovieRepository
import com.example.movietheater.presentation.model.*
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getTopRatedMovies(apiKey: String): TopRatedMoviesResult {
        val response = movieRepository.getTopRatedMovies(apiKey)

        return if (response.isSuccessful) {
            TopRatedMoviesResult.Success(response.body()?.movies?.convertToTopRatedMovieList() ?: listOf())
        } else {
            TopRatedMoviesResult.Error(response.message())
        }
    }

    suspend fun getMovieDetail(id: Int, apiKey: String): MovieDetailResult {
        val response = movieRepository.getMovieDetail(id, apiKey)

        return if (response.isSuccessful) {
            MovieDetailResult.Success(response.body()?.convertToMovieDetailDataView())
        } else {
            MovieDetailResult.Error(response.message())
        }
    }
}