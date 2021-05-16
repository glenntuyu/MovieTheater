package com.example.movietheater.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("3/discover/movie?include_video=false&without_genres=99,10755&vote_count.gte=75&sort_by=vote_count.desc")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): TopRatedMoviesResponse

    @GET("3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieDetailResponse>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
        const val API_KEY = "7e8f60e325cd06e164799af1e317d7a7"
        const val MOVIE_POSTER_URL = "https://image.tmdb.org/t/p/w500"

        fun create(): MovieService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}