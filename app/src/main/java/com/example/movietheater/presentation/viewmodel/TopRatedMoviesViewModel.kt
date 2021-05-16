package com.example.movietheater.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopRatedMoviesViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {

    private val _topRatedMoviesLiveData = MutableLiveData<PagingData<TopRatedMovieDataView>>()
    val topRatedMoviesLiveData: LiveData<PagingData<TopRatedMovieDataView>>
        get() = _topRatedMoviesLiveData

    private val _clickMovieItemEventLiveData = MutableLiveData<Int>()
    val clickMovieItemEventLiveData: LiveData<Int>
        get() = _clickMovieItemEventLiveData

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getTopRatedMovies().collectLatest {
                _topRatedMoviesLiveData.postValue(it)
            }
        }
    }

    fun onMovieItemClicked(id: Int) {
        _clickMovieItemEventLiveData.postValue(id)
    }
}