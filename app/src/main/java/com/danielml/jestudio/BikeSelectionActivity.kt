package com.danielml.jestudio

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.danielml.jestudio.databinding.ActivityBikeSelectionBinding
import com.danielml.jestudio.fragments.BicycleGridFragment
import com.danielml.jestudio.fragments.BicycleGridFragmentDelegate
import com.danielml.jestudio.models.Session

class BikeSelectionActivity : AppCompatActivity(), BicycleGridFragmentDelegate {

  private lateinit var binding: ActivityBikeSelectionBinding
  private lateinit var currentSession: Session
  private var selectedBike: Int = 0
  private val classDataManager = ClassDataManager()

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityBikeSelectionBinding.inflate(layoutInflater)
    binding.topAppBar.setNavigationOnClickListener {
      onBackPressed()
    }
    currentSession = intent.getSerializableExtra("SESSION") as Session
    binding.confirmBikeSelectionButton.isEnabled = false
    binding.confirmBikeSelectionButton.setOnClickListener {
      bookPlace()
    }
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

  private fun bookPlace() {
    val participants = currentSession.participants.toMutableMap() as MutableMap<String, Int>
    val currentUserId = UserSessionManager.getCurrentUser()?.uid
    participants[currentUserId ?: "daniel"] = selectedBike
    classDataManager.bookPlaceIn(currentSession, participants.toMap()) {
      Toast.makeText(this, "Successfully Booked", Toast.LENGTH_SHORT).show()
      val intent = Intent(this, ConfirmationActivity::class.java)
      intent.putExtra("BOOKED_SESSION", currentSession)
      startActivity(intent)
    }
  }

  override fun didSelectBike(selectedBike: String) {
    this.selectedBike = selectedBike.toInt()
    binding.confirmBikeSelectionButton.isEnabled = true
    binding.selectedBikeTV.text = String.format(resources.getString(R.string.bike_selection), selectedBike)
  }
}