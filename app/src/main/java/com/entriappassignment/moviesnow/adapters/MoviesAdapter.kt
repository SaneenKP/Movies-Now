package com.entriappassignment.moviesnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entriappassignment.moviesnow.Constants
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.models.MovieData
import com.entriappassignment.moviesnow.utils.Utils
import kotlinx.android.synthetic.main.movie_view.view.*

class MoviesAdapter() : PagingDataAdapter<MovieData,MoviesAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view , parent , false)
        return MovieViewHolder(view , context = parent.context)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bindData(movie)
            holder.bindImages(movie)
        }
    }

    inner class MovieViewHolder(itemView : View , val context: Context): RecyclerView.ViewHolder(itemView) {

        fun bindData(movieData: MovieData){
            itemView.movieHeadingTv.text = movieData.originalTitle
        }

        fun bindImages(movieData: MovieData){
//            movieData.posterPath?.let { getGlideUrl(it) }?.let { Utils.debug(it) }
            Glide.with(context)
                .load(movieData.posterPath?.let { getGlideUrl(it) })
                .into(itemView.moviePoster)
        }

        private fun getGlideUrl(imagePath : String) : String{
            return Utils.getImageUrl(Constants.GLIDE_IMAGE_SIZE , imagePath)
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