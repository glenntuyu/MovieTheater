package com.example.movietheater.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "topRatedMovies")
data class TopRatedMovieDataView(
    @PrimaryKey @field:SerializedName("id") val movieId: Int = 0,
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("image_url") val imageUrl: String = "",
    @field:SerializedName("vote_average") val voteAverage: Float = 0.0f,
    @field:SerializedName("vote_count") val voteCount: Int = 0,
    @field:SerializedName("overview") val overview: String = "",
    @field:SerializedName("release_date") val releaseDate: Date = Date(),
)