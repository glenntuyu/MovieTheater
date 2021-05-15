package com.example.movietheater.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheater.presentation.MovieItemListener
import com.example.movietheater.presentation.model.TopRatedMovieDataView

class MoviesAdapter(private val listener: MovieItemListener): PagingDataAdapter<TopRatedMovieDataView, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {
        return TopRatedMovieViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movies = getItem(position)
        movies?.let { (holder as TopRatedMovieViewHolder).bind(it) }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<TopRatedMovieDataView>() {
            override fun areItemsTheSame(oldItem: TopRatedMovieDataView, newItem: TopRatedMovieDataView): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TopRatedMovieDataView, newItem: TopRatedMovieDataView): Boolean =
                oldItem == newItem
        }
    }

}