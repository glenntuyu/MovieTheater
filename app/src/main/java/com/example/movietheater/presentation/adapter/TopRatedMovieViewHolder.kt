package com.example.movietheater.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietheater.R
import com.example.movietheater.api.MovieService
import com.example.movietheater.presentation.MovieItemListener
import com.example.movietheater.presentation.model.TopRatedMovieDataView
import com.example.movietheater.util.setTextAndCheckShow
import com.example.movietheater.util.toSimpleString
import kotlinx.android.synthetic.main.top_rated_movie_view_holder.view.*

class TopRatedMovieViewHolder(itemView: View, private val listener: MovieItemListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(element: TopRatedMovieDataView) {
        bindPoster(element)
        bindTitle(element)
        bindReleaseDate(element)
        bindRating(element)
        bindVoteCount(element)
        bindOverview(element)
        bindListener(element)
    }

    private fun bindPoster(data: TopRatedMovieDataView) {
        Glide.with(itemView.context).load(MovieService.MOVIE_POSTER_URL + data.imageUrl).into(itemView.moviePoster)
    }

    private fun bindTitle(data: TopRatedMovieDataView) {
        itemView.movieTitle?.setTextAndCheckShow(data.title)
    }

    private fun bindReleaseDate(data: TopRatedMovieDataView) {
        val date = data.releaseDate.toSimpleString()
        itemView.movieReleaseDate?.setTextAndCheckShow(date)
    }

    private fun bindRating(data: TopRatedMovieDataView) {
        itemView.movieRating?.setTextAndCheckShow("(${data.voteAverage})")
    }

    private fun bindVoteCount(data: TopRatedMovieDataView) {
        itemView.movieVoteCount?.setTextAndCheckShow(data.voteCount.toString())
    }

    private fun bindOverview(data: TopRatedMovieDataView) {
        itemView.movieOverview?.setTextAndCheckShow(data.overview)
    }

    private fun bindListener(data: TopRatedMovieDataView) {
        itemView.topRatedMovieCard?.setOnClickListener {
            listener.onMovieItemClicked(data.movieId)
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.top_rated_movie_view_holder

        fun create(parent: ViewGroup, listener: MovieItemListener): TopRatedMovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(LAYOUT, parent, false)
            return TopRatedMovieViewHolder(view, listener)
        }
    }
}