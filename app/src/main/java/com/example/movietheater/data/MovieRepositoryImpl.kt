package com.example.movietheater.data

import com.example.movietheater.api.MovieDetailResponse
import com.example.movietheater.api.MovieService
import com.example.movietheater.api.TopRatedMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
): MovieRepository {
    override suspend fun getTopRatedMovies(apiKey: String): Response<TopRatedMoviesResponse> {
        return movieService.getTopRatedMovies(apiKey)
    }

    override suspend fun getMovieDetail(id: Int, apiKey: String): Response<MovieDetailResponse> {
        return movieService.getMovieDetail(id, apiKey)
    }
}