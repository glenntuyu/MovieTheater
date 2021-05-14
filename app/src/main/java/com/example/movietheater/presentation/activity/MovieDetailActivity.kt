package com.example.movietheater.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movietheater.R
import com.example.movietheater.api.MovieService
import com.example.movietheater.di.DaggerMovieDetailComponent
import com.example.movietheater.presentation.model.MovieDetailDataView
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModel
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModelFactory
import com.example.movietheater.util.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var movieDetailViewModel: MovieDetailViewModel

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModelFactory

    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerMovieDetailComponent.builder().build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        getExtrasFromIntent()
        initViewModel()
        observeMovieDetailViewModel()
        showLoading()
        processMovieDetail()
    }

    private fun getExtrasFromIntent() {
        movieId = intent.getIntExtra(MOVIE_ID, -1)
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
                    result.data?.let { data ->
                        renderMovieDetail(data)
                        showEmptyList(false)
                    } ?: showEmptyList(true)
                }
                is MovieDetailResult.Error -> {
                    showEmptyList(true)
                    toastError(result.error)
                }
            }
        }
    }

    private fun renderMovieDetail(data: MovieDetailDataView) {
        bindBackdrop(data)
        bindTitle(data)
        bindReleaseDate(data)
        bindDuration(data)
        bindRatingValue(data)
        bindVoteCount(data)
        bindSynopsis(data)
    }

    private fun bindBackdrop(data: MovieDetailDataView) {
        Glide.with(this).load(MovieService.MOVIE_POSTER_URL + data.backdropPath).into(movieDetailBackdrop)
    }

    private fun bindTitle(data: MovieDetailDataView) {
        movieDetailTitle?.setTextAndCheckShow(data.title)
    }

    private fun bindReleaseDate(data: MovieDetailDataView) {
        val date = data.releaseDate.toSimpleString()
        movieDetailReleaseDate?.setTextAndCheckShow(date)
    }

    private fun bindDuration(data: MovieDetailDataView) {
        movieDetailDuration?.setTextAndCheckShow("${data.runtime} minutes")
    }

    private fun bindRatingValue(data: MovieDetailDataView) {
        movieDetailRatingValue?.setTextAndCheckShow(data.voteAverage.toString())
    }

    private fun bindVoteCount(data: MovieDetailDataView) {
        movieDetailVoteCount?.setTextAndCheckShow(data.voteCount.toString())
    }

    private fun bindSynopsis(data: MovieDetailDataView) {
        movieDetailSynopsisText?.setTextAndCheckShow(data.overView)
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            hideLoading()
            hideMovieDetailView()
            showNoResultView()
        }
        else {
            hideLoading()
            showMovieDetailView()
            hideNoResultView()
        }
    }

    private fun hideMovieDetailView() {
        movieDetailView?.gone()
    }

    private fun showMovieDetailView() {
        movieDetailView?.visible()
    }

    private fun showNoResultView() {
        movieDetailRefreshButton?.visible()
        movieDetailErrorText?.visible()
    }

    private fun hideNoResultView() {
        movieDetailRefreshButton?.gone()
        movieDetailErrorText?.gone()
    }

    private fun showLoading() {
        movieDetailProgressBar?.show()
        movieDetailProgressBar?.visible()
    }

    private fun hideLoading() {
        movieDetailProgressBar?.hide()
        movieDetailProgressBar?.gone()
    }

    private fun toastError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun processMovieDetail() {
        movieDetailViewModel.getMovieDetail(movieId)
    }
}