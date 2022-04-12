package com.danielml.jestudio.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielml.jestudio.R
import com.danielml.jestudio.databinding.FragmentProfileBinding
import com.danielml.jestudio.models.SpinningStudio

class BookFragment : Fragment() {

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
    rvStudios?.adapter = StudiosAdapter(view?.context, createSpinningStudios())
    rvStudios?.layoutManager = LinearLayoutManager(activity)
    return view
  }

  private fun createSpinningStudios(): ArrayList<SpinningStudio> {
    val studios = ArrayList<SpinningStudio>()
    studios.add(SpinningStudio("Moderna", 10))
    studios.add(SpinningStudio("Universidad 54", 8))
    studios.add(SpinningStudio("Quemada", 4))
    return studios
  }
}