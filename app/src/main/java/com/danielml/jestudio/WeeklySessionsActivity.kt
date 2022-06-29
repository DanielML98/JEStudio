package com.danielml.jestudio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielml.jestudio.databinding.ActivityWeeklySessionsBinding
import com.danielml.jestudio.models.Session

class WeeklySessionsActivity : AppCompatActivity(), WeeklySessionsAdapter.OnSessionClick {

  private lateinit var binding: ActivityWeeklySessionsBinding
  private var sessions: ArrayList<Session> = ArrayList()
  private val dataManager: ClassDataManager = ClassDataManager()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWeeklySessionsBinding.inflate(layoutInflater)
    binding.topAppBar.setNavigationOnClickListener {
      onBackPressed()
    }
    fetchSessions()
    setContentView(binding.root)
  }

  private fun fetchSessions() {
    this.sessions = dataManager.getSessions { 
      this.setUpRecyclerView()
      Toast.makeText(this, "Sessions Assigned", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setUpRecyclerView() {
    binding.weeklySessionsRV.adapter = WeeklySessionsAdapter(this, sessions, this)
    binding.weeklySessionsRV.layoutManager = LinearLayoutManager(this)
  }

  override fun onSessionClick(session: Session) {
    Toast.makeText(this, "${session.coach}", Toast.LENGTH_SHORT).show()
  }

}