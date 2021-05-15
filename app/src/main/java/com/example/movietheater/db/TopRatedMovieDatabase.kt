package com.example.movietheater.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.util.Converters

@Database(
    entities = [TopRatedMovieDataView::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TopRatedMovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): TopRatedMovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: TopRatedMovieDatabase? = null

        fun getInstance(context: Context): TopRatedMovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TopRatedMovieDatabase::class.java, "movieTheater.db"
            )
                .build()
    }
}
