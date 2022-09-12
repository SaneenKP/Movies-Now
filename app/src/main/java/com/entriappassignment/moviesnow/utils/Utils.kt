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

        fun getImageUrl(width : String , movieImagePath : String) : String{
           return BuildConfig.IMAGE_BASE_URL + width + movieImagePath
        }

    }

}