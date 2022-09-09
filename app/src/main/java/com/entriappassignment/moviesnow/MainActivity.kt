package com.entriappassignment.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.adapters.NowPlayingMoviesAdapter
import com.entriappassignment.moviesnow.helpers.NowPlayingMoviesResponse
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.models.NowPlayingMoviesData
import com.entriappassignment.moviesnow.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.entriappassignment.moviesnow.Constants.Companion.Status
import com.entriappassignment.moviesnow.Constants.Companion.Status.LOADING
import com.entriappassignment.moviesnow.Constants.Companion.Status.ERROR
import com.entriappassignment.moviesnow.Constants.Companion.Status.SUCCESS
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var movieViewModel : MoviesViewModel
    private var moviesAdapter : NowPlayingMoviesAdapter? = null
    private var nowPlayingMovieList : MutableList<MovieData> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init(){

        movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        movieViewModel.refresh()

        moviesAdapter = NowPlayingMoviesAdapter(nowPlayingMovieList)

        moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        movieViewModel.getResponse().observe(this) {
            handleResponseState(it)
        }
    }

    private fun handleResponseState(response: NowPlayingMoviesResponse){
        when(response.status){

            LOADING -> {
                Utils.toast(this , "loading")
            }

            SUCCESS -> {
                Utils.toast(this , response.data.toString())
            }

            ERROR -> {
                Utils.toast(this , "error")
            }
        }
    }

}

