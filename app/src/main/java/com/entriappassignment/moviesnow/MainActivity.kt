package com.entriappassignment.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.viewmodels.MoviesViewModel
import com.entriappassignment.moviesnow.adapters.LoaderAdapter
import com.entriappassignment.moviesnow.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    lateinit var movieViewModel : MoviesViewModel
    lateinit var moviesAdapter : MoviesAdapter
    lateinit var movieRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init(){

        movieRecyclerView = findViewById(R.id.moviesRecyclerView)
        moviesAdapter = MoviesAdapter()
        movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.setHasFixedSize(true)
        movieRecyclerView.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        movieViewModel.refresh()
        observeViewModel()
    }

    private fun observeViewModel(){
        movieViewModel.getMovieList()?.observe(this) {
            moviesAdapter.submitData(lifecycle, it)
        }
    }
}

