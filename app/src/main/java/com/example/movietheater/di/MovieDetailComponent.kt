package com.example.movietheater.di

import android.content.Context
import com.example.movietheater.presentation.activity.MovieDetailActivity
import dagger.BindsInstance
import dagger.Component

@MovieScope
@Component(modules = [
    MovieUseCaseModule::class,
    MovieDetailViewModelModule::class,
    MovieRepositoryModule::class,
    MovieServiceModule::class,
    MovieDatabaseModule::class,
])
interface MovieDetailComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): MovieDetailComponent
    }

    fun inject(app: MovieDetailActivity)
}