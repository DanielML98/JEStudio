package com.danielml.jestudio.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielml.jestudio.R
import com.danielml.jestudio.WeeklySessionsActivity
import com.danielml.jestudio.databinding.FragmentProfileBinding
import com.danielml.jestudio.models.SpinningStudio
import com.danielml.jestudio.models.Studio

class BookFragment : Fragment(), StudiosAdapter.OnStudioClick {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_book, container, false)
    val rvStudios = view?.findViewById<RecyclerView>(R.id.rvStudios)
    rvStudios?.adapter = StudiosAdapter(view?.context, createSpinningStudios(), this)
    rvStudios?.layoutManager = LinearLayoutManager(activity)
    return view
  }

  private fun createSpinningStudios(): ArrayList<SpinningStudio> {
    val studios = ArrayList<SpinningStudio>()
    studios.add(SpinningStudio("Moderna", 10))
    studios.add(SpinningStudio("Universidad 54", 8))
    return studios
  }

  private fun getSelectedStudio(studioName: String): Studio {
    when (studioName) {
      "Moderna" -> return Studio.MODERNA
      "Universidad 54" -> return Studio.UNIVERSIDAD
    }
    return Studio.QUEMADA
  }

  override fun onStudioClick(studio: SpinningStudio) {
    Toast.makeText(context, "${studio.name}", Toast.LENGTH_SHORT).show()
    val intent: Intent = Intent(context, WeeklySessionsActivity::class.java)
    intent.putExtra("SELECTED_STUDIO", getSelectedStudio(studio.name))
    startActivity(intent)
  }
}