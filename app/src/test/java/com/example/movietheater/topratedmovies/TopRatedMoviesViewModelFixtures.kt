package com.example.movietheater.topratedmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.movietheater.api.TopRatedMoviesResponse
import com.example.movietheater.utils.jsonToObject
import com.example.movietheater.presentation.model.convertToTopRatedMovieList
import com.example.movietheater.presentation.viewmodel.TopRatedMoviesViewModel
import com.example.movietheater.usecase.MovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

internal open class TopRatedMoviesViewModelFixtures {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    protected val movieUseCase = mockk<MovieUseCase>(relaxed = true)

    protected lateinit var topRatedMoviesViewModel: TopRatedMoviesViewModel

    protected val topRatedMoviesResponseJson = "top_rated_movies_response.json"
    protected val topRatedMoviesResponse = topRatedMoviesResponseJson.jsonToObject<TopRatedMoviesResponse>()

    @Before
    open fun setUp() {
        Dispatchers.setMain(dispatcher)
        topRatedMoviesViewModel = createTopRatedMoviesViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    protected open fun createTopRatedMoviesViewModel(): TopRatedMoviesViewModel {
        return TopRatedMoviesViewModel(
                movieUseCase,
        )
    }

    protected fun `Given API service will be successful`(topRatedMoviesResponse: TopRatedMoviesResponse) {
        val data = PagingData.from(topRatedMoviesResponse.movies.convertToTopRatedMovieList())
        coEvery {
            movieUseCase.getTopRatedMovies()
        } returns flowOf(data)
    }

    protected fun `Given API call will fail`() {
        coEvery {
            movieUseCase.getTopRatedMovies()
        } returns flowOf()
    }
}