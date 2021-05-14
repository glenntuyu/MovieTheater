package com.example.movietheater.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModel
import com.example.movietheater.presentation.viewmodel.MovieDetailViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailViewModelModule {
    @MovieScope
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: MovieDetailViewModelFactory): ViewModelProvider.Factory

    @MovieScope
    @Binds
    internal abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}