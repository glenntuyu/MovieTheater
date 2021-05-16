package com.example.movietheater.topratedmovies

import androidx.paging.PagingData
import com.example.movietheater.api.TopRatedMoviesResponse
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.utils.*
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
        val expected = topRatedMoviesResponse
        `Given API service will be successful`(expected)
        `When view call getTopRatedMovies`()
        `Then verify response`(expected)
    }

    private fun `Then verify response`(expected: TopRatedMoviesResponse) = runBlockingTest {
        val value = topRatedMoviesViewModel.topRatedMoviesLiveData.getOrAwaitValue()

        value.shouldBeInstanceOf<PagingData<TopRatedMovieDataView>>()

        val data = value.collectData()
        data.size shouldBe 20
        data.assertMovieDataView(expected)
    }

    private fun List<TopRatedMovieDataView>.assertMovieDataView(expected: TopRatedMoviesResponse) {
        var index = 0
        forEach { data ->
            data.movieId shouldBe expected.movies[index].movieId
            data.title shouldBe expected.movies[index].title
            data.imageUrl shouldBe expected.movies[index].imageUrl
            data.voteAverage shouldBe expected.movies[index].voteAverage
            data.voteCount shouldBe expected.movies[index].voteCount
            data.overview shouldBe expected.movies[index].overview
            data.releaseDate shouldBe expected.movies[index].releaseDate
            index++
        }
    }

    @Test
    fun `Test get More Top Rated Movies`() {
        val expected = topRatedMoviesResponse
        val expected2 = moreTopRatedMoviesResponse
        `Given API service will be successful`(expected, expected2)
        `When view call more getTopRatedMovies`()
        `Then verify response for more top rated movies`(expected2)
    }

    private fun `When view call more getTopRatedMovies`() = runBlockingTest {
        topRatedMoviesViewModel.getTopRatedMovies()
        topRatedMoviesViewModel.getTopRatedMovies()
    }

    private fun `Then verify response for more top rated movies`(
        expected: TopRatedMoviesResponse
    ) = runBlockingTest {
        val value = topRatedMoviesViewModel.topRatedMoviesLiveData.getOrAwaitValue()

        value.shouldBeInstanceOf<PagingData<TopRatedMovieDataView>>()

        val data = value.collectData()
        data.size shouldBe 20
        data.assertMovieDataView(expected)
    }
}