package com.example.movietheater

import com.example.movietheater.api.MovieDetailResponse
import com.example.movietheater.presentation.model.MovieDetailDataView
import com.example.movietheater.presentation.model.MovieDetailResult
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

internal class HandleGetMovieDetailTest: MovieDetailViewModelFixtures() {

    @Test
    fun `Error network when getting movie detial`() {
        `Given API call will fail`()
        `When view call getTopRatedMovies`()
        `Then verify response error`()
    }

    private fun `When view call getTopRatedMovies`() = runBlockingTest {
        movieDetailViewModel.getMovieDetail(13)
    }

    private fun `Then verify response error`() {
        val value = movieDetailViewModel.movieDetailLiveData.getOrAwaitValue()

        value.shouldBeInstanceOf<MovieDetailResult.Error>()
    }

    @Test
    fun `Test get movie detail`() {
        `Given API call will be successful`(movieDetailData)
        `When view call getTopRatedMovies`()
        `Then verify response`()
    }

    private fun `Then verify response`() {
        val value = movieDetailViewModel.movieDetailLiveData.getOrAwaitValue()

        value.shouldBeInstanceOf<MovieDetailResult.Success>()
        (value as MovieDetailResult.Success).data?.assertMovieDetail(movieDetailData)
    }

    private fun MovieDetailDataView.assertMovieDetail(expected: MovieDetailResponse) {
        isAdult shouldBe expected.isAdult
        backdropPath shouldBe expected.backdropPath
        title shouldBe expected.title
        voteAverage shouldBe expected.voteAverage
        voteCount shouldBe expected.voteCount
        overView shouldBe expected.overView
        releaseDate shouldBe expected.releaseDate
        runtime shouldBe expected.runtime
    }
}