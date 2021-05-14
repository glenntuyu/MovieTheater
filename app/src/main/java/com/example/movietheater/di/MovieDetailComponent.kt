package com.example.movietheater.di

import com.example.movietheater.presentation.activity.MovieDetailActivity
import dagger.Component

@MovieScope
@Component(modules = [
    MovieUseCaseModule::class,
    MovieDetailViewModelModule::class,
    MovieRepositoryModule::class,
    MovieServiceModule::class,
])
interface MovieDetailComponent {
    fun inject(app: MovieDetailActivity)
}