package com.example.movietheater.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietheater.R
import com.example.movietheater.api.TopRatedMoviesResult
import com.example.movietheater.di.DaggerTopRatedMoviesComponent
import com.example.movietheater.presentation.MovieItemListener
import com.example.movietheater.presentation.adapter.MoviesAdapter
import com.example.movietheater.presentation.model.TopRatedMovieDataView
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
        val component = DaggerTopRatedMoviesComponent.builder().build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        setContentView(R.layout.activity_main)
        processTopRatedMovies()
        setListener()
        getTopRatedMovies()
    }

    private fun initViewModel() {
        topRatedMoviesViewModel = run {
            ViewModelProvider(this, topRatedMoviesViewModelFactory)
                .get(TopRatedMoviesViewModel::class.java)
        }
    }

    private fun processTopRatedMovies() {
        observeTopRatedMoviesViewModel()
        initAdapter()
    }

    private fun observeTopRatedMoviesViewModel() {
        topRatedMoviesViewModel.topRatedMoviesLiveData.observe(this) { result ->
            when (result) {
                is TopRatedMoviesResult.Success -> {
                    submitList(result.data)
                    showEmptyList(result.data.isEmpty())
                }
                is TopRatedMoviesResult.Error -> {
                    showEmptyList(true)
                    toastError(result.error)
                }
            }
        }
    }

    private fun submitList(data: List<TopRatedMovieDataView>) {
        adapter?.submitList(data)
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

    private fun toastError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initAdapter() {
        adapter = MoviesAdapter(this)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
    }

    private fun setListener() {
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
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID, id)
        startActivity(intent)
    }
}