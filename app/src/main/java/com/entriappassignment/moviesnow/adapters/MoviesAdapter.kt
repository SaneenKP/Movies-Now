package com.entriappassignment.moviesnow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.models.MovieData
import kotlinx.android.synthetic.main.movie_view.view.*

class MoviesAdapter() : PagingDataAdapter<MovieData,MoviesAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view , parent , false)
        return MovieViewHolder(view)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bindData(movie)
        }
    }

    inner class MovieViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        fun bindData(movieData: MovieData){
            itemView.movieHeadingTv.text = movieData.originalTitle
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