package com.example.movietheater.data

import com.example.movietheater.api.MovieDetailResponse
import com.example.movietheater.api.TopRatedMoviesResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getTopRatedMovies(apiKey: String): Response<TopRatedMoviesResponse>
    suspend fun getMovieDetail(id: Int, apiKey: String): Response<MovieDetailResponse>
}