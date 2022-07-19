package com.danielml.jestudio.fragments

import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.danielml.jestudio.BikeGridModel
import com.danielml.jestudio.R
import com.danielml.jestudio.databinding.FragmentBicycleGridBinding


class BicycleGridFragment : Fragment(), View.OnClickListener {

  private lateinit var binding: FragmentBicycleGridBinding
  lateinit var gridModel: BikeGridModel
  var selectedBicycle: ImageButton? = null
  var delegate: BicycleGridFragmentDelegate? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentBicycleGridBinding.inflate(inflater, container, false)
    setButtonsListeners()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setGridAppearance()
  }

  private fun setButtonsListeners() {
      binding.bike1.setOnClickListener(this)
      binding.bike2.setOnClickListener(this)
      binding.bike3.setOnClickListener(this)
      binding.bike4.setOnClickListener(this)
      binding.bike5.setOnClickListener(this)
      binding.bike6.setOnClickListener(this)
      binding.bike7.setOnClickListener(this)
      binding.bike8.setOnClickListener(this)
      //setGridAppearance()
  }

  private fun setGridAppearance() {
    Log.i("Configuration Participants🐙", "${gridModel.bikeDistribution.values}")
    for (bikeNumber in gridModel.bikeDistribution.values) {
      Log.i("Setting number🐙", "${bikeNumber}")
      val bikeImage = view?.findViewWithTag<ImageButton>(bikeNumber.toString())
      bikeImage?.isClickable = false
      bikeImage?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.je_red_500))
    }
  }

  override fun onClick(view: View?) {
    selectedBicycle?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
    setGridAppearance()
    var imageButton = view as ImageButton
    selectedBicycle = imageButton
    selectedBicycle?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.je_purple_300))
    delegate?.didSelectBike(selectedBicycle!!.tag.toString())
  }
}

interface BicycleGridFragmentDelegate {
  fun didSelectBike(selectedBike: String)
}