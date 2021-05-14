package com.example.movietheater.di

import com.example.movietheater.presentation.activity.MainActivity
import dagger.Component

@MovieScope
@Component(modules = [
    MovieUseCaseModule::class,
    TopRatedMoviesViewModelModule::class,
    MovieRepositoryModule::class,
    MovieServiceModule::class,
])
interface TopRatedMoviesComponent {
    fun inject(app: MainActivity)
}