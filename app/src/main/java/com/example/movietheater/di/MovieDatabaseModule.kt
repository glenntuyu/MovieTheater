package com.example.movietheater.di

import android.content.Context
import com.example.movietheater.db.TopRatedMovieDatabase
import dagger.Module
import dagger.Provides

@Module
class MovieDatabaseModule {
    @MovieScope
    @Provides
    fun provideMovieDatabase(context: Context): TopRatedMovieDatabase =
        TopRatedMovieDatabase.getInstance(context)
}
