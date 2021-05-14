package com.example.movietheater.di

import com.example.movietheater.api.MovieService
import com.example.movietheater.data.MovieRepository
import com.example.movietheater.data.MovieRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MovieRepositoryModule {

    @MovieScope
    @Provides
    internal fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieService,
        )
    }
}