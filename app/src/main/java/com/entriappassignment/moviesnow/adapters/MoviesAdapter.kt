package com.entriappassignment.moviesnow.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.databinding.MovieViewBinding
import com.entriappassignment.moviesnow.models.NowPlayingMovieResult
import com.entriappassignment.moviesnow.singleton.HasLoadedDataState
import com.entriappassignment.moviesnow.ui.MovieDetails
import com.entriappassignment.moviesnow.utils.Constants

class MoviesAdapter() : PagingDataAdapter<NowPlayingMovieResult,MoviesAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return MovieViewHolder(context = parent.context , binding)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            HasLoadedDataState.setHasLoadedData(true)
            holder.bindData(movie)
        }
    }

    inner class MovieViewHolder(private val context: Context, private val movieViewDataBinding : MovieViewBinding)
        : RecyclerView.ViewHolder(movieViewDataBinding.root){

        private var movie : NowPlayingMovieResult? = null

        init {
            movieViewDataBinding.root.setOnClickListener{
                // TODO: "implement movie details screen"
                handleItemClick()
            }
        }

        fun bindData(nowPlayingMovieResult: NowPlayingMovieResult){
            this.movie = nowPlayingMovieResult
            movieViewDataBinding.movie = this.movie
        }

        private fun handleItemClick(){
            val intent = Intent(context, MovieDetails::class.java)
            intent.putExtra(Constants.MOVIE_ID , this.movie?.id)
            context.startActivity(intent)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<NowPlayingMovieResult>(){

            override fun areItemsTheSame(oldItem: NowPlayingMovieResult, newItem: NowPlayingMovieResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NowPlayingMovieResult, newItem: NowPlayingMovieResult): Boolean {
                return oldItem == newItem
            }

        }
    }
}