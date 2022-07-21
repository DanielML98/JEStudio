package com.danielml.jestudio

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class LauncherActivity : AppCompatActivity() {

  private val networkRequest: NetworkRequest = NetworkRequest.Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

  private val networkCallback = object : ConnectivityManager.NetworkCallback() {
    // lost network connection
    override fun onLost(network: Network) {
      super.onLost(network)
      showLostConnectionToast()
      presentNoNetworkScreen()
    }

    override fun onUnavailable() {
      super.onUnavailable()
      presentNoNetworkScreen()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_launcher)
  }

  override fun onStart() {
    super.onStart()
    val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    connectivityManager.requestNetwork(networkRequest, networkCallback,  1000)
    checkLoginFlow()
  }

  private fun checkLoginFlow() {
    val user = UserSessionManager.getCurrentUser()
    if (user != null) {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    } else {
      startActivity(Intent(this, LoginActivity::class.java))
    }
  }

  private fun showLostConnectionToast() {
    Toast.makeText(this, "Seems like you lost connection, some features may not be available", Toast.LENGTH_LONG).show()
  }

  private fun presentNoNetworkScreen() {
    startActivity(Intent(this, NoInternetConnectionActivity::class.java))
    finish()
  }

}