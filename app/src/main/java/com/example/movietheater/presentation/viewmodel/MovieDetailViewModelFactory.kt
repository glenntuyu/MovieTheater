package com.example.movietheater.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietheater.usecase.MovieUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class MovieDetailViewModelFactory @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieUseCase) as T
    }
}