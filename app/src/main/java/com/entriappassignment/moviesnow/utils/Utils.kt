package com.entriappassignment.moviesnow.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.entriappassignment.moviesnow.BuildConfig

class Utils {

    companion object {

        val TAG : String = "movies_now"
        val E_TAG : String = "Error"

        fun toast(context : Context , message:String){
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
        }

        fun debug(message: String){
            Log.d(TAG , message)
        }

        fun error(message: String){
            Log.d(E_TAG , message)
        }

        private fun getImageUrl(width : String, movieImagePath : String) : String{
           return BuildConfig.IMAGE_BASE_URL + width + movieImagePath
        }

        fun getGlideUrl(imagePath : String) : String{
            return getImageUrl(Constants.GLIDE_IMAGE_SIZE , imagePath)
        }

        fun convertMovieRuntimeFormatToString(runtime : Int) : String{
            return if(runtime < 60){
                "$runtime min"
            } else{
                var hour : Int = runtime/60
                var minutes : Int = runtime % 60
                ""+hour+"hr "+minutes+" min"
            }
        }
        //change the ratings to the multiple of 5 , so that it can be fit in the rating view.
        fun calculateRating(rating: Double): Double {
            return (rating * 5) / 10
        }


    }

}