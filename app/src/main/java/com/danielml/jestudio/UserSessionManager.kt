package com.danielml.jestudio

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UserSessionManager {
  val authentication: FirebaseAuth = FirebaseAuth.getInstance()
  fun getCurrentUser(): FirebaseUser? = authentication.currentUser
}