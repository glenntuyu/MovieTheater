package com.example.movietheater.presentation.model

import com.example.movietheater.api.TopRatedMoviesResponse

fun List<TopRatedMoviesResponse.Movie>.convertToTopRatedMovieList(): List<TopRatedMovieDataView> {
    val list = ArrayList<TopRatedMovieDataView>()
    for(item in this) {
        val movie = TopRatedMovieDataView(
            id = item.id,
            title = item.title,
            imageUrl = item.imageUrl,
            voteAverage = item.voteAverage,
            voteCount = item.voteCount,
            overview = item.overview,
            releaseDate = item.releaseDate,
        )
        list.add(movie)
    }
    return list
}