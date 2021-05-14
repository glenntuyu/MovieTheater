package com.example.movietheater.di

import com.example.movietheater.api.MovieService
import dagger.Module
import dagger.Provides

@Module
class MovieServiceModule {
    @MovieScope
    @Provides
    fun provideMovieService(): MovieService =
        MovieService.create()
}
