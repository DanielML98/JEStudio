package com.danielml.jestudio

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun String.getVerbalDate(format: String): String {
  val date = LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
  return "${date.dayOfWeek}, ${date.month} ${date.dayOfMonth} ${date.year}"
}