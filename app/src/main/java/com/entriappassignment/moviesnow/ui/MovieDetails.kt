package com.entriappassignment.moviesnow.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.util.Util
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.databinding.ActivityMovieDetailsBinding
import com.entriappassignment.moviesnow.models.MovieDetailsData
import com.entriappassignment.moviesnow.singleton.InternetConnectivityState
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Constants.Companion.Status
import com.entriappassignment.moviesnow.utils.Utils
import com.entriappassignment.moviesnow.viewmodels.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetails : AppCompatActivity(), View.OnClickListener {

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

    private fun bindData(data : MovieDetailsData?){

        this.movieDetailsData = data
        if (!movieDetailsContainer.isVisible){
            somethingWentWrongContainer.visibility = View.GONE
            noConnectionContainer.visibility = View.GONE
            movieDetailsContainer.visibility = View.VISIBLE
        }

        //if movie details is null , show something went wrong
        if (this.movieDetailsData != null){
            formatAndChangeMovieData()
        }else{
            movieDetailsContainer.visibility = View.GONE
            somethingWentWrongContainer.visibility = View.VISIBLE
        }

    }

    private fun formatAndChangeMovieData(){

        //change the tagline value by adding double quotations
        this.movieDetailsData!!.tagline = "\"${this.movieDetailsData!!.tagline}\""
        movieDetailsBinding.movieDetails = this.movieDetailsData

        //change vote average value to fit the rating view.
        this.movieDetailsData!!.voteAverage = Utils.calculateRating(this.movieDetailsData!!.voteAverage!!)

        //binding movies runtime after formatting it.
        movieDetailsBinding.movieRuntime.text = Utils.convertMovieRuntimeFormatToString(this.movieDetailsData!!.runtime!!)

        //convert release date format and bind
        this.movieDetailsData!!.releaseDate = Utils.convertFormatReleaseDate(this.movieDetailsData!!.releaseDate)

        //combine all the genres into a single string and bind the string.
        this.movieDetailsData!!.finalGenreString = Utils.getMovieGenreString(this.movieDetailsData!!.genres)
    }


    private fun handleIntent(){
        movieId = intent.getIntExtra(Constants.MOVIE_ID , 0)
    }

    private fun init(){

        //change the status bar color.
        val window = window
        window.statusBarColor =  applicationContext.resources.getColor(R.color.black)
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        movieDetailsButton.setOnClickListener(this)
    }

    private fun setupObservers(){
        //Observe the movieDetails Retrieved.
        movieDetailsViewModel.getMovieDetails(this.movieId)
        movieDetailsViewModel.movieDetailsResponse.observe(this , Observer {

           it?.let { response ->

               when(response.status){

                   Status.LOADING -> {
                        loadingStatus.visibility = View.VISIBLE
                   }

                   Status.SUCCESS -> {
                       loadingStatus.visibility = View.GONE
                       movieDetailsContainer.visibility = View.VISIBLE

                       //bind the data received.
                       bindData(response.data)
                   }

                   Status.ERROR -> {

                       loadingStatus.visibility = View.GONE
                       movieDetailsContainer.visibility = View.GONE

                       //if there is no internet show the internet connectivity error
                       //else since something else might have caused the error, show the somethingWentWrong error.
                       if (InternetConnectivityState.getInternetConnectivityState()) somethingWentWrongContainer.visibility = View.VISIBLE
                       else noConnectionContainer.visibility = View.VISIBLE
                   }
               }
           }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
           R.id.movieDetailsButton -> {
               //open the Url
               val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.movieDetailsData?.homepage))
               startActivity(browserIntent)
           }
        }
    }

}