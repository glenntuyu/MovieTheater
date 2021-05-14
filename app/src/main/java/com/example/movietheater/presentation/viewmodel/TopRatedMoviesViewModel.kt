package com.example.movietheater.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheater.api.MovieService
import com.example.movietheater.presentation.model.TopRatedMoviesResult
import com.example.movietheater.usecase.MovieUseCase
import com.example.movietheater.util.launchCatchError
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TopRatedMoviesViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {

    private val _topRatedMoviesLiveData = MutableLiveData<TopRatedMoviesResult>()
    val topRatedMoviesLiveData: LiveData<TopRatedMoviesResult>
        get() = _topRatedMoviesLiveData

    fun getTopRatedMovies() {
        viewModelScope.launchCatchError(Dispatchers.IO, {
            val data = movieUseCase.getTopRatedMovies(MovieService.API_KEY)
            _topRatedMoviesLiveData.postValue(data)
        }) {
            it.message?.let { message ->
                _topRatedMoviesLiveData.postValue(TopRatedMoviesResult.Error(message))
            }
        }
    }
}