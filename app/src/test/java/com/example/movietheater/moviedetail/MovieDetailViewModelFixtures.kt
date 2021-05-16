package com.example.movietheater.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietheater.api.MovieDetailResponse
import com.example.movietheater.utils.jsonToObject
import com.example.movietheater.presentation.model.MovieDetailResult
import com.example.movietheater.presentation.model.convertToMovieDetailDataView
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModel
import com.example.movietheater.usecase.MovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

internal open class MovieDetailViewModelFixtures {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val movieUseCase = mockk<MovieUseCase>(relaxed = true)

    protected lateinit var movieDetailViewModel: MovieDetailViewModel

    protected val movieDetailResponse = "movie_detail_response.json"
    protected val movieDetailData = movieDetailResponse.jsonToObject<MovieDetailResponse>()

    @Before
    open fun setUp() {
        movieDetailViewModel = createMovieDetailViewModel()
    }

    protected open fun createMovieDetailViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel(
                movieUseCase,
        )
    }

    protected fun `Given API call will be successful`(topRatedMovies: MovieDetailResponse) {
        coEvery {
            movieUseCase.getMovieDetail(any())
        } returns MovieDetailResult.Success(topRatedMovies.convertToMovieDetailDataView())
    }

    protected fun `Given API call will fail`() {
        coEvery {
            movieUseCase.getMovieDetail(any())
        } returns MovieDetailResult.Error("Fail to get Movie Detail")
    }
}