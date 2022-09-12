package com.entriappassignment.moviesnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entriappassignment.moviesnow.Constants
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.databinding.MovieViewBinding
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.utils.Utils
import kotlinx.android.synthetic.main.movie_view.view.*

class MoviesAdapter() : PagingDataAdapter<MovieData,MoviesAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return MovieViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bindData(movie)
        }
    }

    inner class MovieViewHolder(private val movieViewDataBinding : MovieViewBinding)
        : RecyclerView.ViewHolder(movieViewDataBinding.root) {

        fun bindData(movieData: MovieData){
            movieViewDataBinding.movie = movieData
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