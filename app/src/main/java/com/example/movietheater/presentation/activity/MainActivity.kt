package com.example.movietheater.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movietheater.R
import com.example.movietheater.di.DaggerTopRatedMoviesComponent
import com.example.movietheater.presentation.MovieItemListener
import com.example.movietheater.presentation.adapter.MoviesAdapter
import com.example.movietheater.presentation.viewmodel.TopRatedMoviesViewModel
import com.example.movietheater.presentation.viewmodel.TopRatedMoviesViewModelFactory
import com.example.movietheater.util.MOVIE_ID
import com.example.movietheater.util.gone
import com.example.movietheater.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MovieItemListener {
    @Inject
    lateinit var topRatedMoviesViewModel: TopRatedMoviesViewModel

    @Inject
    lateinit var topRatedMoviesViewModelFactory: TopRatedMoviesViewModelFactory

    private var adapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerTopRatedMoviesComponent.factory().create(applicationContext)
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        observeLiveData()
        initAdapter()
        setRefreshButtonListener()
        getTopRatedMovies()
    }

    private fun initViewModel() {
        topRatedMoviesViewModel = run {
            ViewModelProvider(this, topRatedMoviesViewModelFactory)
                .get(TopRatedMoviesViewModel::class.java)
        }
    }

    private fun observeLiveData() {
        observeTopRatedMoviesLiveData()
        observeClickMovieItemEventLiveData()
    }

    private fun observeTopRatedMoviesLiveData() {
        topRatedMoviesViewModel.topRatedMoviesLiveData.observe(this) {
                showEmptyList(false)
                adapter?.submitData(lifecycle, it)
        }
    }

    private fun observeClickMovieItemEventLiveData() {
        topRatedMoviesViewModel.clickMovieItemEventLiveData.observe(this) {
            moveToMovieDetailActivity(it)
        }
    }

    private fun moveToMovieDetailActivity(id: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID, id)
        startActivity(intent)
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            hideLoading()
            hideRecyclerView()
            showNoResultView()
        }
        else {
            hideLoading()
            showRecyclerView()
            hideNoResultView()
        }
    }

    private fun hideRecyclerView() {
        recyclerView?.gone()
    }

    private fun showRecyclerView() {
        recyclerView?.visible()
    }

    private fun showNoResultView() {
        refreshButton?.visible()
        noResultText?.visible()
    }

    private fun hideNoResultView() {
        refreshButton?.gone()
        noResultText?.gone()
    }

    private fun initAdapter() {
        adapter = MoviesAdapter(this)
        recyclerView?.adapter = adapter
    }

    private fun setRefreshButtonListener() {
        refreshButton?.setOnClickListener {
            getTopRatedMovies()
        }
    }

    private fun getTopRatedMovies() {
        showLoading()
        hideRecyclerView()
        hideNoResultView()
        topRatedMoviesViewModel.getTopRatedMovies()
    }

    private fun showLoading() {
        progressBar?.show()
        progressBar?.visible()
    }

    private fun hideLoading() {
        progressBar?.hide()
        progressBar?.gone()
    }

    override fun onMovieItemClicked(id: Int) {
        topRatedMoviesViewModel.onMovieItemClicked(id)
    }
}