package com.entriappassignment.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.entriappassignment.moviesnow.viewmodels.MoviesViewModel
import com.entriappassignment.moviesnow.adapters.LoaderAdapter
import com.entriappassignment.moviesnow.adapters.MoviesAdapter
import com.entriappassignment.moviesnow.utils.ConnectionLiveStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

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
        observeViewModel()
    }

    private fun observeConnectivity(){
        connectivityLiveStatus.observe(this , Observer {status ->
            handleConnectivityChange(status)
        })
    }

    private fun observeViewModel(){
        movieViewModel.movieList.observe(this) {
            moviesAdapter.submitData(lifecycle, it)
            if (swipeToRefresh.isRefreshing) swipeToRefresh.isRefreshing = false
        }
    }

    private fun handleConnectivityChange(status : Boolean){
        networkConnectivityStatusTv.visibility = if (status) View.GONE else View.VISIBLE
    }

    override fun onRefresh() {
        moviesAdapter.refresh()
    }

}

