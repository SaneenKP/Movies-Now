package com.entriappassignment.moviesnow.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.viewmodels.MoviesViewModel
import com.entriappassignment.moviesnow.adapters.LoaderAdapter
import com.entriappassignment.moviesnow.adapters.MoviesAdapter
import com.entriappassignment.moviesnow.utils.ConnectionLiveStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener{

    lateinit var movieViewModel : MoviesViewModel
    lateinit var moviesAdapter : MoviesAdapter
    lateinit var movieRecyclerView: RecyclerView
    lateinit var connectivityLiveStatus: ConnectionLiveStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){

        connectivityLiveStatus = ConnectionLiveStatus(this)
        observeConnectivity()

        swipeToRefresh.setOnRefreshListener(this)

        movieRecyclerView = findViewById(R.id.moviesRecyclerView)
        moviesAdapter = MoviesAdapter()
        movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.setHasFixedSize(true)
        movieRecyclerView.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        nowPlayingTV.setOnClickListener(this)
        observeViewModel()
    }

    //observe connectivity change
    private fun observeConnectivity(){
        connectivityLiveStatus.observe(this , Observer {status ->
            handleConnectivityChange(status)
        })
    }

    //Observe the movie data change
    private fun observeViewModel(){
        movieViewModel.movieList.observe(this) {
            moviesAdapter.submitData(lifecycle, it)
            if (swipeToRefresh.isRefreshing) swipeToRefresh.isRefreshing = false
        }
    }

    private fun handleConnectivityChange(status : Boolean){
        networkConnectivityStatusTv.visibility = if (status) View.INVISIBLE else View.VISIBLE
        nowPlayingTV.visibility = if (status) View.VISIBLE else View.GONE
        moviesAdapter.retry()

        //change the status bar color according to network status.
        val window = window
        window.statusBarColor = if (status) applicationContext.resources.getColor(R.color.app_background_color) else applicationContext.resources.getColor(
            R.color.network_connectivity_alert_color
        )
    }

    //refresh when swipe
    override fun onRefresh() {
        moviesAdapter.refresh()
    }

    override fun onClick(p0: View?) {
       when(p0?.id) {
           R.id.nowPlayingTV -> {
               movieRecyclerView.smoothScrollToPosition(0)
           }
       }
    }

}

