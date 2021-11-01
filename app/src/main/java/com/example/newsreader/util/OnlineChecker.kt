package com.example.newsreader.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class OnlineChecker
    constructor(private val connectivityManager: ConnectivityManager): IOnlineChecker {
    override fun isOnline(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}