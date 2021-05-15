package com.example.movietheater.di

import com.example.movietheater.api.MovieService
import com.example.movietheater.data.MovieRepository
import com.example.movietheater.data.MovieRepositoryImpl
import com.example.movietheater.db.TopRatedMovieDatabase
import dagger.Module
import dagger.Provides

@Module
class MovieRepositoryModule {

    @MovieScope
    @Provides
    internal fun provideMovieRepository(
        movieService: MovieService,
        movieDatabase: TopRatedMovieDatabase,
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieService,
            movieDatabase
        )
    }
}