package com.danielml.jestudio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danielml.jestudio.databinding.ActivityNoInternetConnectionBinding

class NoInternetConnectionActivity : AppCompatActivity() {

  lateinit var binding: ActivityNoInternetConnectionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNoInternetConnectionBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

}