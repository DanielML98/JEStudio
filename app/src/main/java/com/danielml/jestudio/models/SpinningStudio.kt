package com.danielml.jestudio.models

import java.io.Serializable

data class SpinningStudio(
  val name: String,
  val capacity: Int
)

enum class Studio(val raw: String): Serializable {
  MODERNA("moderna"),
  UNIVERSIDAD("universidad"),
  QUEMADA("quemada")
}
