package com.danielml.jestudio

import android.util.Log
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

}