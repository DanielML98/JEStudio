package com.danielml.jestudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.danielml.jestudio.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setUpListeners()
    setContentView(binding.root)
  }
  private fun setUpListeners() {
    with(binding) {
      signInButton.setOnClickListener { checkCredentials() }
      signUpButton.setOnClickListener {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
      }
    }
  }

  private fun checkCredentials() {
    if(textFieldsAreValid()) {
      UserSessionManager.authentication.signInWithEmailAndPassword(
        binding.userTextField.editText?.text.toString(),
        binding.passwordTextField.editText?.text.toString()
      )
        .addOnCompleteListener {
          startActivity(Intent(this, MainActivity::class.java))
          finish()
      }
        .addOnFailureListener {
          Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
        }
    } else {
      Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
    }
  }

  private fun textFieldsAreValid(): Boolean {
    val email: String = binding.userTextField.editText?.text.toString()
    val password: String = binding.passwordTextField.editText?.text.toString()
    return !(email.isEmpty() or password.isEmpty())
  }
}
