package com.example.movietheater.presentation.model

import java.util.*

data class TopRatedMovieDataView(
    val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val voteAverage: Float = 0.0f,
    val voteCount: Int = 0,
    val overview: String = "",
    val releaseDate: Date = Date(),
)