package com.danielml.jestudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LauncherActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_launcher)
  }

  override fun onStart() {
    super.onStart()
    val user = UserSessionManager.getCurrentUser()
    if (user != null) {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    } else {
      startActivity(Intent(this, LoginActivity::class.java))
    }
  }
}