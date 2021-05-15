package com.example.movietheater.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietheater.presentation.model.TopRatedMovieDataView

@Dao
interface TopRatedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<TopRatedMovieDataView>)

    @Query(
        "SELECT * FROM topRatedMovies " +
            "ORDER BY voteAverage DESC, title ASC"
    )
    fun getMovies(): PagingSource<Int, TopRatedMovieDataView>

    @Query("DELETE FROM topRatedMovies")
    suspend fun clearMovies()
}
