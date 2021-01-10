package io.minoro75.test_123.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
	//checks connection to wifi\mobile network

	fun isNetworkConnected(): Boolean {
		val connectivityManager =
			context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val networkCapabilities = connectivityManager.activeNetwork ?: return false
		val activeNetwork =
			connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

		return when {
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
			else -> false
		}
	}
}