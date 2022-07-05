package com.danielml.jestudio

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.danielml.jestudio.models.Session
import com.danielml.jestudio.models.Studio

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ClassDataManager {
  private var database: DatabaseReference = Firebase.database.reference

  fun getSessions(fromStudio: Studio, completion: () -> Unit): ArrayList<Session> {
    var weeklySessions = ArrayList<Session>()
    database.child(fromStudio.raw).get().addOnSuccessListener {
      val sessions = it.value as Map<String, Session>
      val gson = Gson()
      val json = Gson().toJson(sessions)
      val mapType: Type = object : TypeToken<Map<String, Session>>() {}.type
      var data  = gson.fromJson<Map<String, Session>>(json, mapType)
      for (session in data.values) {
        weeklySessions.add(session)
      }
      weeklySessions.sortByDescending {
        it.hour
      }
      completion()
    }.addOnFailureListener {
      Log.d("EXCEPTION➡️", it.toString())
    }
    return weeklySessions
  }

    fun bookPlaceIn(session: Session, updatedParticipants: Map<String, Int>, completion: () -> Unit) {
      database.child(session.studio).child(getSessionId(session)).child("participants").setValue(updatedParticipants)
        .addOnSuccessListener {
          completion()
        }
        .addOnFailureListener {
          Log.i("🆔", "Error❌: $it")
        }
    }

   private fun getSessionId(session: Session): String {
    val studio = session.studio.first()
    val date = session.date.filter { it.toString() != "/" }
    val hour = session.hour.removeSuffix(":00")
    return "$studio$date$hour"
  }

}