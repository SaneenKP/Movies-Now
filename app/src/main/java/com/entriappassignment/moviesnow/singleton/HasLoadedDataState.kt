package com.entriappassignment.moviesnow.singleton

/***
 * This pattern is just for this simple use case , as this should be made a habit
 * This is a singleton pattern , and singleton patterns creates issues during testing
 * it also creates a strong coupling of dependencies
 */

//holds the status of , if there is any data in the adapter or not .
object HasLoadedDataState {
    private var hasLoadedData : Boolean = false
    fun getHasLoadedData() : Boolean {return this.hasLoadedData
    }
    fun setHasLoadedData(status : Boolean){ hasLoadedData = status}
}