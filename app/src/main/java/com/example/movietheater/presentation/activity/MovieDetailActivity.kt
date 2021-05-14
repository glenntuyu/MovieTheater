package com.example.movietheater.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movietheater.R
import com.example.movietheater.di.DaggerMovieDetailComponent
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.presentation.model.TopRatedMoviesResult
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModel
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModelFactory
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var movieDetailViewModel: MovieDetailViewModel

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerMovieDetailComponent.builder().build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initViewModel()
        observeMovieDetailViewModel()
    }

    private fun initViewModel() {
        movieDetailViewModel = run {
            ViewModelProvider(this, movieDetailViewModelFactory)
                .get(MovieDetailViewModel::class.java)
        }
    }

    private fun observeMovieDetailViewModel() {
        movieDetailViewModel.movieDetailLiveData.observe(this) { result ->
            when (result) {
                is MovieDetailResult.Success -> {

                }
                is MovieDetailResult.Error -> {

                }
            }
        }
    }
}