package com.entriappassignment.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Constants.Companion.Status
import com.entriappassignment.moviesnow.utils.Utils
import com.entriappassignment.moviesnow.viewmodels.MovieDetailsViewModel

class MovieDetails : AppCompatActivity() {

    private var movieId : Int = 0;
    lateinit var movieDetailsViewModel : MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        handleIntent()
        init()
        setupObservers()


    }

    private fun handleIntent(){
        movieId = intent.getIntExtra(Constants.MOVIE_ID , 0)
    }

    private fun init(){
        val window = window
        window.statusBarColor =  applicationContext.resources.getColor(R.color.app_background_color)

        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

    }

    private fun setupObservers(){
        movieDetailsViewModel.getMovieDetails(this.movieId)
        movieDetailsViewModel.movieDetailsResponse.observe(this , Observer {

           it?.let { response ->

               when(response.status){

                   Status.LOADING -> {
                        Utils.debug("loading movie details")
                   }

                   Status.SUCCESS -> {
                       Utils.debug("moves details success ${response.data}")
                   }

                   Status.ERROR -> {
                       Utils.debug("movie details error ${response.message}")
                   }
               }
           }
        })
    }

}