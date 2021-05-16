package com.example.movietheater.presentation.model

import com.example.movietheater.api.MovieDetailResponse

fun MovieDetailResponse.convertToMovieDetailDataView(): MovieDetailDataView {
    return MovieDetailDataView(
        isAdult = this.isAdult,
        backdropPath = this.backdropPath ?: "",
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        overView = this.overView,
        releaseDate = this.releaseDate,
        runtime = this.runtime,
    )
}