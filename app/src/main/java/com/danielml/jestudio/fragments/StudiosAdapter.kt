package com.danielml.jestudio.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danielml.jestudio.R
import com.danielml.jestudio.models.SpinningStudio

class StudiosAdapter(private val context: Context?, private val spinningStudios: ArrayList<SpinningStudio>) : RecyclerView.Adapter<StudiosAdapter.ViewHolder>() {

  //Create a new view - Expensive cause it's creating a new view instead of recycling it
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = LayoutInflater.from(context).inflate(R.layout.item_spinning_studio, parent, false)
    return ViewHolder(view)
  }
  // Bind the data at position into the viewholder
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val studio = spinningStudios[position]
    holder.bind(studio)
  }

  override fun getItemCount() = spinningStudios.size

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvStudioName: TextView = itemView.findViewById<TextView>(R.id.tvStudioName)
    private val tvStudioCapacity: TextView = itemView.findViewById<TextView>(R.id.tvCapacity)

    fun bind(studio: SpinningStudio) {
      tvStudioName.text = studio.name
      tvStudioCapacity.text = "${context?.getString(R.string.capacity)}: ${studio.capacity} ${context?.getString(R.string.bikes)}"
    }
  }
}
