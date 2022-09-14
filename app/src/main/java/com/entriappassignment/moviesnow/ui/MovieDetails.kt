package com.entriappassignment.moviesnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.databinding.ActivityMovieDetailsBinding
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.singleton.InternetConnectivityState
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Constants.Companion.Status
import com.entriappassignment.moviesnow.utils.Utils
import com.entriappassignment.moviesnow.viewmodels.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    private var movieId : Int = 0;
    lateinit var movieDetailsViewModel : MovieDetailsViewModel
    private var movieDetailsData : MovieDetailsData? = null
    private lateinit var movieDetailsBinding : ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailsBinding = DataBindingUtil.setContentView(this , R.layout.activity_movie_details)

        handleIntent()
        init()
        setupObservers()

    }

    private fun bindData(){
        if (!movieDetailsContainer.isVisible){
            noConnectionContainer.visibility = View.GONE
            movieDetailsContainer.visibility = View.VISIBLE
        }
        movieDetailsBinding.movieDetails = this.movieDetailsData
    }

    private fun handleIntent(){
        movieId = intent.getIntExtra(Constants.MOVIE_ID , 0)
    }

    private fun init(){

        val window = window
        window.statusBarColor =  applicationContext.resources.getColor(R.color.app_background_color)
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        if (!InternetConnectivityState.getInternetConnectivityState()){
            movieDetailsContainer.visibility = View.INVISIBLE
            noConnectionContainer.visibility = View.VISIBLE
        }

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
                       this.movieDetailsData = response.data
                       bindData()
                   }

                   Status.ERROR -> {
                       Utils.debug("movie details error ${response.message}")
                   }
               }
           }
        })
    }

}