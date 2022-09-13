package com.entriappassignment.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.entriappassignment.moviesnow.utils.ConnectionLiveStatus
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    private var movieId : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        handleIntent()
        init()

    }

    private fun init(){
        val window = window
        window.statusBarColor =  applicationContext.resources.getColor(R.color.app_background_color)

    }

    private fun handleIntent(){
        movieId = intent.getIntExtra(Constants.MOVIE_ID , 0)
    }
}