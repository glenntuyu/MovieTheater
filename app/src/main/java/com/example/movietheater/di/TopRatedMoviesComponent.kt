package com.example.movietheater.di

import android.content.Context
import com.example.movietheater.presentation.activity.MainActivity
import dagger.BindsInstance
import dagger.Component

@MovieScope
@Component(modules = [
    MovieUseCaseModule::class,
    TopRatedMoviesViewModelModule::class,
    MovieRepositoryModule::class,
    MovieServiceModule::class,
    MovieDatabaseModule::class,
])
interface TopRatedMoviesComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): TopRatedMoviesComponent
    }

    fun inject(app: MainActivity)
}