package com.danielml.jestudio

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielml.jestudio.databinding.ActivityWeeklySessionsBinding
import com.danielml.jestudio.fragments.BicycleGridFragmentDelegate
import com.danielml.jestudio.models.Session
import com.danielml.jestudio.models.Studio

class WeeklySessionsActivity : AppCompatActivity(), WeeklySessionsAdapter.OnSessionClick{

  private lateinit var binding: ActivityWeeklySessionsBinding
  private var sessions: ArrayList<Session> = ArrayList()
  private val dataManager: ClassDataManager = ClassDataManager()
  private lateinit var selectedStudio: Studio

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWeeklySessionsBinding.inflate(layoutInflater)
    selectedStudio = intent.getSerializableExtra("SELECTED_STUDIO") as Studio
    binding.topAppBar.setNavigationOnClickListener {
      onBackPressed()
    }
    fetchSessions()
    setContentView(binding.root)
  }

  private fun fetchSessions() {
    this.sessions = dataManager.getSessions(selectedStudio) {
      this.setUpRecyclerView()
      Toast.makeText(this, "Sessions Assigned", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setUpRecyclerView() {
    binding.weeklySessionsRV.adapter = WeeklySessionsAdapter(this, sessions, this)
    binding.weeklySessionsRV.layoutManager = LinearLayoutManager(this)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onSessionClick(session: Session) {
    val participants: Map<String, Int> = session.participants as Map<String, Int>
    val gridModel = BikeGridModel(participants)
    val intent = Intent(this, BikeSelectionActivity::class.java)
    intent.putExtra("DISTRIBUTION", gridModel)
    intent.putExtra("SESSION", session)
    //Log.i("🆔", ClassDataManager().getSessionId(session))
    startActivity(intent)
  }

}