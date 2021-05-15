package com.example.movietheater.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedMoviesViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {

    fun getTopRatedMovies(): Flow<PagingData<TopRatedMovieDataView>> {
        return movieUseCase.getTopRatedMovies().cachedIn(viewModelScope)
    }
}