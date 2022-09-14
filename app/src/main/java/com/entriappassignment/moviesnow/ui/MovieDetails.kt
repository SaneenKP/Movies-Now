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
            noConnectionContainer.visibility = View.GONE
            movieDetailsContainer.visibility = View.VISIBLE
        }

        if (this.movieDetailsData != null){

            //change the tagline value by adding double quotations
            this.movieDetailsData!!.tagline = "\"${this.movieDetailsData!!.tagline}\""
            movieDetailsBinding.movieDetails = this.movieDetailsData

            //change vote average value to fit the rating view.
            this.movieDetailsData!!.voteAverage = Utils.calculateRating(this.movieDetailsData!!.voteAverage!!)

            movieDetailsBinding.movieRuntime.text = Utils.convertMovieRuntimeFormatToString(this.movieDetailsData!!.runtime!!)

            this.movieDetailsData!!.releaseDate = Utils.convertFormatReleaseDate(this.movieDetailsData!!.releaseDate)
        }
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

        movieDetailsButton.setOnClickListener(this)
    }

    private fun setupObservers(){
        movieDetailsViewModel.getMovieDetails(this.movieId)
        movieDetailsViewModel.movieDetailsResponse.observe(this , Observer {

           it?.let { response ->

               when(response.status){

                   Status.LOADING -> {
                        loadingStatus.visibility = View.VISIBLE
                        Utils.debug("loading movie details")
                   }

                   Status.SUCCESS -> {
                       loadingStatus.visibility = View.GONE
                       movieDetailsContainer.visibility = View.VISIBLE
                       bindData(response.data)
                   }

                   Status.ERROR -> {
                       loadingStatus.visibility = View.GONE
                       movieDetailsContainer.visibility = View.GONE
                       somethingWentWrongContainer.visibility = View.VISIBLE
                       Utils.debug("movie details error ${response.message}")
                   }
               }
           }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
           R.id.movieDetailsButton -> {
               val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.movieDetailsData?.homepage))
               startActivity(browserIntent)
           }
        }
    }

}