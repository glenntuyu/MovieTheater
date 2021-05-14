package com.example.movietheater.di

import com.example.movietheater.data.MovieRepository
import com.example.movietheater.usecase.MovieUseCase
import dagger.Module
import dagger.Provides

@Module
class MovieUseCaseModule {
    @MovieScope
    @Provides
    fun provideMovieUseCase(repository: MovieRepository): MovieUseCase =
        MovieUseCase(repository)
}
