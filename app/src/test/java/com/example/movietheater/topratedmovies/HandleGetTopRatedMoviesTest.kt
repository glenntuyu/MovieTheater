package com.example.movietheater.topratedmovies

import androidx.paging.PagingData
import com.example.movietheater.utils.getOrAwaitValue
import com.example.movietheater.utils.observerOnChangedNotCalled
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.utils.shouldBeInstanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


@ExperimentalCoroutinesApi
internal class HandleGetTopRatedMoviesTest: TopRatedMoviesViewModelFixtures() {

    @Test
    fun `Error network when getting Top Rated Movies`() {
        `Given API call will fail`()
        `When view call getTopRatedMovies`()
        `Then verify no changes on UI`()
    }

    private fun `When view call getTopRatedMovies`() = runBlockingTest {
        topRatedMoviesViewModel.getTopRatedMovies()
    }

    private fun `Then verify no changes on UI`() {
        assert(topRatedMoviesViewModel.topRatedMoviesLiveData.observerOnChangedNotCalled())
    }

    @Test
    fun `Test get Top Rated Movies`() {
        `Given API service will be successful`(topRatedMoviesResponse)
        `When view call getTopRatedMovies`()
        `Then verify response`()
    }

    private fun `Then verify response`() {
        val value = topRatedMoviesViewModel.topRatedMoviesLiveData.getOrAwaitValue()

        value.shouldBeInstanceOf<PagingData<TopRatedMovieDataView>>()
    }
}