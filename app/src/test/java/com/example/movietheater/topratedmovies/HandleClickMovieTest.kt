package com.example.movietheater.topratedmovies

import com.example.movietheater.utils.getOrAwaitValue
import com.example.movietheater.utils.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class HandleClickMovieTest: TopRatedMoviesViewModelFixtures() {

    @Test
    fun `Test get Top Rated Movies`() {
        `Given API service will be successful`(topRatedMoviesResponse)
        `Given view will call getTopRatedMovies`()

        val id = topRatedMoviesResponse.movies[0].movieId
        `When view click movie`(id)
        `Then verify interaction`(id)
    }

    private fun `Given view will call getTopRatedMovies`() = runBlockingTest {
        topRatedMoviesViewModel.getTopRatedMovies()
    }

    private fun `When view click movie`(id: Int) {
        topRatedMoviesViewModel.onMovieItemClicked(id)
    }

    private fun `Then verify interaction`(id: Int) {
        val value = topRatedMoviesViewModel.clickMovieItemEventLiveData.getOrAwaitValue()

        value shouldBe id
    }
}