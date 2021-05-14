package com.example.movietheater.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheater.api.MovieService
import com.example.movietheater.api.TopRatedMoviesResult
import com.example.movietheater.usecase.MovieUseCase
import com.example.movietheater.util.launchCatchError
import kotlinx.coroutines.Dispatchers

class TopRatedMoviesViewModel(
    private val movieUseCase: MovieUseCase,
): ViewModel() {

    private val _topRatedMoviesLiveData = MutableLiveData<TopRatedMoviesResult>()
    val topRatedMovieLiveData: LiveData<TopRatedMoviesResult>
        get() = _topRatedMoviesLiveData

    fun getTopRatedMovies() {
        viewModelScope.launchCatchError(Dispatchers.IO, {
            val data = movieUseCase.getTopRatedMovies(MovieService.API_KEY)
            _topRatedMoviesLiveData.postValue(data)
        }) {
            Log.d("ViewModel", "Error fetching data")
            Log.d("ViewModel", "${it.message}")
        }
    }
}