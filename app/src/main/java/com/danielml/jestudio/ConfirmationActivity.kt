package com.danielml.jestudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import com.danielml.jestudio.databinding.ActivityConfirmationBinding
import com.danielml.jestudio.models.Session
import java.util.*

class ConfirmationActivity : AppCompatActivity() {

  lateinit var binding: ActivityConfirmationBinding
  lateinit var bookedSession: Session

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityConfirmationBinding.inflate(layoutInflater)
    bookedSession = intent.getSerializableExtra("BOOKED_SESSION") as Session
    setUpUI()
    setContentView(binding.root)
  }

  private fun setUpUI() {
    binding.addToCalendarButton.setOnClickListener {
      addEventToCalendar()
    }
    binding.okButton.setOnClickListener {
      navigateUpTo(Intent(this, MainActivity::class.java))
    }
    binding.dateTV.text = bookedSession.date.getVerbalDate("dd/MM/yyyy")
    binding.hourTV.text = bookedSession.hour
  }

  fun addEventToCalendar() {
    val hour = bookedSession.hour.split(":")
    val date = bookedSession.date.split("/")
    val startMillis: Long = Calendar.getInstance().run {
      set(date[2].toInt(), date[1].toInt() - 1, date[0].toInt(), hour[0].toInt(), hour[1].toInt())
      timeInMillis
    }
    val intent = Intent(Intent.ACTION_INSERT)
      .setData(CalendarContract.Events.CONTENT_URI)
      .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
      .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startMillis + 60 * 60 * 1000)
      .putExtra(CalendarContract.Events.TITLE, getString(R.string.calendar_event_title))
      .putExtra(CalendarContract.Events.DESCRIPTION, getString(R.string.calendar_event_description))
      .putExtra(
        CalendarContract.Events.EVENT_LOCATION,
        "${getString(R.string.app_name)} ${bookedSession.studio.capitalize()}"
      )
    startActivity(intent)
  }
}