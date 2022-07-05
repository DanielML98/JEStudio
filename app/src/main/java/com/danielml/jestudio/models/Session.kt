package com.danielml.jestudio.models

import java.io.Serializable

data class Session(
  var coach: String = "",
  var date: String = "",
  var hour: String = "",
  var participants: Map<String, Any> = emptyMap(),
  var studio: String = ""
): Serializable
