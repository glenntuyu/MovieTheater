package com.example.movietheater.presentation.model

import java.util.*

data class MovieDetailDataView(
    val isAdult: Boolean = false,
    val backdropPath: String = "",
    val title: String = "",
    val voteAverage: Float = 0.0f,
    val voteCount: Int = 0,
    val overView: String = "",
    val releaseDate: Date = Date(),
    val runtime: Int = 0,
)