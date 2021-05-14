package com.example.movietheater.api

import com.example.movietheater.presentation.model.TopRatedMovieDataView

sealed class TopRatedMoviesResult {
    data class Success(val data: List<TopRatedMovieDataView>) : TopRatedMoviesResult()
    data class Error(val error: String) : TopRatedMoviesResult()
}