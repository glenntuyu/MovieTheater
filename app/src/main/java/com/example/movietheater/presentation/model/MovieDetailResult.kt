package com.example.movietheater.presentation.model

sealed class MovieDetailResult {
    data class Success(val data: MovieDetailDataView?) : MovieDetailResult()
    data class Error(val error: String) : MovieDetailResult()
}