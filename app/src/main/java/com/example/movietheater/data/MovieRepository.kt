package com.example.movietheater.data

import androidx.paging.PagingData
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMovies(): Flow<PagingData<TopRatedMovieDataView>>
    suspend fun getMovieDetail(id: Int): MovieDetailResult
}