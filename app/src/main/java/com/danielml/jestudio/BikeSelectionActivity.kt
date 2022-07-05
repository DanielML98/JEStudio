package com.danielml.jestudio

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.danielml.jestudio.databinding.ActivityBikeSelectionBinding
import com.danielml.jestudio.fragments.BicycleGridFragment
import com.danielml.jestudio.fragments.BicycleGridFragmentDelegate
import com.danielml.jestudio.models.Session
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BikeSelectionActivity : AppCompatActivity(), BicycleGridFragmentDelegate {

  private lateinit var binding: ActivityBikeSelectionBinding
  private lateinit var currentSession: Session

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityBikeSelectionBinding.inflate(layoutInflater)
    binding.topAppBar.setNavigationOnClickListener {
      onBackPressed()
    }
    currentSession = intent.getSerializableExtra("SESSION") as Session
    setUpGrid()
    setUpSessionInfoCell()
    setContentView(binding.root)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun setUpSessionInfoCell() {
    binding.tvSessionStudio.text = currentSession.studio.capitalize()
    binding.tvCoachName.text = currentSession.coach
    binding.tvSessionDay.text = currentSession.date.getVerbalDate("dd/MM/yyyy")
    binding.tvSessionTime.text = currentSession.hour
  }

  private fun setUpGrid() {
    val grid = supportFragmentManager.findFragmentById(binding.bicycleGrid.id) as BicycleGridFragment
    grid.delegate = this
    grid.gridModel = intent.getSerializableExtra("DISTRIBUTION") as BikeGridModel
  }
}