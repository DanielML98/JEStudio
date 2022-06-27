package com.danielml.jestudio.models

data class Session(
  var coach: String = "",
  var date: String = "",
  var hour: String = "",
  var participants: Map<String, Any> = emptyMap()
)
