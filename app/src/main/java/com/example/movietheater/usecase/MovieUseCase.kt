package com.example.movietheater.usecase

import com.example.movietheater.api.TopRatedMoviesResult
import com.example.movietheater.data.MovieRepository
import com.example.movietheater.presentation.model.convertToTopRatedMovieList

class MovieUseCase (private val movieRepository: MovieRepository) {

    suspend fun getTopRatedMovies(apiKey: String): TopRatedMoviesResult {
        val response = movieRepository.getTopRatedMovies(apiKey)

        return if (response.isSuccessful) {
            TopRatedMoviesResult.Success(response.body()?.movies?.convertToTopRatedMovieList() ?: listOf())
        } else {
            TopRatedMoviesResult.Error(response.message())
        }
    }
}