package com.entriappassignment.moviesnow.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.MovieDetails
import com.entriappassignment.moviesnow.databinding.MovieViewBinding
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.utils.Constants
import com.entriappassignment.moviesnow.utils.Utils

class MoviesAdapter() : PagingDataAdapter<MovieData,MoviesAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return MovieViewHolder(context = parent.context , binding)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bindData(movie)
        }
    }

    inner class MovieViewHolder(private val context: Context, private val movieViewDataBinding : MovieViewBinding)
        : RecyclerView.ViewHolder(movieViewDataBinding.root){

        private var movie : MovieData? = null

        init {
            movieViewDataBinding.root.setOnClickListener{
                // TODO: "implement movie details screen"
                handleItemClick()
            }
        }

        fun bindData(movieData: MovieData){
            this.movie = movieData
            movieViewDataBinding.movie = calculateRating(movieData)
        }

        private fun handleItemClick(){
            val intent = Intent(context, MovieDetails::class.java)
            intent.putExtra(Constants.MOVIE_ID , this.movie?.id)
            context.startActivity(intent)
        }

        //change the ratings to the multiple of 5 , so that it can be fit in the rating view.
        private fun calculateRating(movieData: MovieData) : MovieData{
           movieData.voteAverage = (movieData.voteAverage?.times(5))?.div(10)
            return movieData
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieData>(){

            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem == newItem
            }

        }
    }
}