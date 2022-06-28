package com.danielml.jestudio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.danielml.jestudio.databinding.ActivityMainBinding
import com.danielml.jestudio.fragments.BookFragment
import com.danielml.jestudio.fragments.PaymentsFragment
import com.danielml.jestudio.fragments.ProfileFragment
import com.danielml.jestudio.models.SpinningStudio

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val profileFragment = ProfileFragment()
  private val paymentsFragment = PaymentsFragment()
  private val bookFragment = BookFragment()
  private val dataManager: ClassDataManager = ClassDataManager()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    replaceFragment(profileFragment)
    binding.bottomNavigation.setOnItemSelectedListener {
      when(it.itemId) {
        R.id.ic_book -> replaceFragment(bookFragment)
        R.id.ic_payments -> replaceFragment(paymentsFragment)
        R.id.ic_profile -> replaceFragment(profileFragment)
      }
      true
    }
    dataManager.getSessions()
  }

  private fun replaceFragment(fragment: Fragment) {
    if(fragment != null) {
      val userName: String? = UserSessionManager.getCurrentUser()?.displayName
      if (userName!= null) {
        Log.i("FIREBASE NAME 🔥", userName)
      }
      val transaction = supportFragmentManager.beginTransaction()
      transaction.replace(binding.fragmentContainer.id, fragment)
      transaction.commit()
    }
  }

}