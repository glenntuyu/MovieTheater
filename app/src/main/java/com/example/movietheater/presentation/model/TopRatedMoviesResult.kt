package com.example.movietheater.presentation.model

sealed class TopRatedMoviesResult {
    data class Success(val data: List<TopRatedMovieDataView>) : TopRatedMoviesResult()
    data class Error(val error: String) : TopRatedMoviesResult()
}