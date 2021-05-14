package com.example.movietheater.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheater.api.MovieService
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.usecase.MovieUseCase
import com.example.movietheater.util.launchCatchError
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _movieDetailLiveData = MutableLiveData<MovieDetailResult>()
    val movieDetailLiveData: LiveData<MovieDetailResult>
        get() = _movieDetailLiveData

    fun getMovieDetail(id: Int) {
        viewModelScope.launchCatchError(Dispatchers.IO, {
            val data = movieUseCase.getMovieDetail(id, MovieService.API_KEY)
            _movieDetailLiveData.postValue(data)
        }) {
            it.message?.let { message ->
                _movieDetailLiveData.postValue(MovieDetailResult.Error(message))
            }
        }
    }
}