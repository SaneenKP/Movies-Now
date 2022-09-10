package com.entriappassignment.moviesnow.adapters

import android.media.tv.TvContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.entriappassignment.moviesnow.R

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loader , parent , false)
        return LoaderHolder(view)
    }

    override fun onBindViewHolder(holder: LoaderHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val progress = itemView.findViewById<ProgressBar>(R.id.movieProgressBar)

        fun bind(loadState: LoadState){
           progress.isVisible = loadState is LoadState.Loading
        }

    }

}