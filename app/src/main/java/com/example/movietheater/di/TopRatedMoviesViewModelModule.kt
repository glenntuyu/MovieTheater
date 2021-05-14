package com.example.movietheater.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietheater.presentation.viewmodel.TopRatedMoviesViewModel
import com.example.movietheater.presentation.viewmodel.TopRatedMoviesViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class TopRatedMoviesViewModelModule {
    @MovieScope
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: TopRatedMoviesViewModelFactory): ViewModelProvider.Factory

    @MovieScope
    @Binds
    internal abstract fun bindTopRatedMoviesViewModel(viewModel: TopRatedMoviesViewModel): ViewModel
}