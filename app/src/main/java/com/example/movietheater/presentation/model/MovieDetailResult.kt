package com.example.movietheater.presentation.model

import com.example.movietheater.api.MovieDetailResponse

sealed class MovieDetailResult {
    data class Success(val data: MovieDetailDataView) : MovieDetailResult()
    data class Error(val error: String) : MovieDetailResult()
}