package com.entriappassignment.moviesnow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.singleton.InternetConnectivityState
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Utils
import com.entriappassignment.moviesnow.viewmodels.MoviesViewModel
import com.entriappassignment.moviesnow.viewmodels.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    lateinit var splashScreenViewModel : SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]

        Handler().postDelayed({
            observeViewModel()
        },Constants.SPLASH_SCREEN_DELAY_TIME)
    }

    private fun observeViewModel(){
        splashScreenViewModel.getConfiguration()
        splashScreenViewModel.configurationResponse.observe(this , Observer {

            it?.let { response ->

                when(response.status){

                    Constants.Companion.Status.LOADING -> {
                        progress.visibility = View.VISIBLE
                    }

                    Constants.Companion.Status.SUCCESS -> {
                        Utils.debug(response.data.toString())
                        startMainActivity()
                    }

                    Constants.Companion.Status.ERROR -> {
                        Utils.toast(this , applicationContext.resources.getString(R.string.something_went_wrong_text))
                        startMainActivity()
                    }
                }
            }
        })
    }

    private fun startMainActivity(){
        var intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}