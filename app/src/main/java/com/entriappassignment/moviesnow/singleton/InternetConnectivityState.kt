package com.entriappassignment.moviesnow.singleton

/***
 * This pattern is just for this simple use case , as this should be made a habit
 * This is a singleton pattern , and singleton patterns creates issues during testing
 * it also creates a strong coupling of dependencies
 */

//Holds the state of the internet connection.
object InternetConnectivityState {
    private var internetConnectivityState : Boolean = false
    fun setInternetConnectivityState(status : Boolean){ this.internetConnectivityState = status}
    fun getInternetConnectivityState() : Boolean {return internetConnectivityState }
}