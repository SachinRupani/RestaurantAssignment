package com.manektech.restaurant.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.manektech.restaurant.R
import timber.log.Timber

/**
 * Function to check if internet connection is on/off
 * @return true -> On, false -> Off
 */
fun Context.isNetworkOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
    }
    return false
}

/**
 * Function to display the error message in snack bar
 */
fun View.showRedErrorSnackBar(strErrMsg: String) {
    Snackbar.make(this, strErrMsg, Snackbar.LENGTH_LONG).apply {
        val snackBarTextView =
            view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?
        snackBarTextView?.setTextColor(ContextCompat.getColor(view.context, R.color.fix_red_error))
        show()
    }
}

