package com.danielml.jestudio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danielml.jestudio.models.Session

class WeeklySessionsAdapter(private val context: Context, private val sessions: ArrayList<Session>, private val onSessionListener: OnSessionClick) : RecyclerView.Adapter<WeeklySessionsAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_weekly_session, parent, false)
    return ViewHolder(view, onSessionListener)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentSession: Session = sessions[position]
    holder.bind(currentSession)
  }

  override fun getItemCount(): Int = sessions.size

  inner class ViewHolder(itemView: View, private val onSessionClickListener: OnSessionClick): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    fun bind(session: Session) {
      val hourTextView = itemView.findViewById<TextView>(R.id.tvHour)
      val availableSpotsTextView = itemView.findViewById<TextView>(R.id.tvAvailableSpots)
      val coachTextView: TextView = itemView.findViewById(R.id.tvCoachName)

      hourTextView.text = session.hour
      availableSpotsTextView.text = session.date
      coachTextView.text = session.coach
      itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
      onSessionClickListener.onSessionClick(sessions[bindingAdapterPosition])
    }

  }


  interface OnSessionClick {
    fun onSessionClick(session: Session)
  }
}
