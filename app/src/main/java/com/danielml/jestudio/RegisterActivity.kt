package com.danielml.jestudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.danielml.jestudio.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

  private lateinit var binding: ActivityRegisterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    binding.signUpButton.setOnClickListener { 
      evaluateRegistrationForm()
    }
    setContentView(binding.root)
  }

  private fun evaluateRegistrationForm() {
    when (textFieldsAreValid()){
      RegistrationErrors.EMPTY_FIELDS -> showEmptyFieldsWarning()
      RegistrationErrors.INVALID_EMAIL -> showInvalidEmailWarning()
      RegistrationErrors.INVALID_PASSWORD -> showInvalidPasswordWarning()
      RegistrationErrors.PASSWORD_MISMATCH -> showPasswordMismatchWarning()
      RegistrationErrors.NONE -> signUp()
    }
  }

  private fun signUp() {
    UserSessionManager.authentication.createUserWithEmailAndPassword(
      binding.userTextField.editText?.text.toString(),
      binding.passwordTextField.editText?.text.toString()
    )
      .addOnSuccessListener {
        setNewUserProfile()
        startActivity(Intent(this, MainActivity::class.java ))
        finish()
      }
      .addOnFailureListener {
        Log.d("FIREBASE EXCEPTION🔥", it.toString())
        Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
      }
  }
  
  private fun showEmptyFieldsWarning() {
    Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
  }
  
  private fun showInvalidEmailWarning() {
    binding.userTextField.error = "Invalid email"
    Toast.makeText(this, "Your email format is invalid", Toast.LENGTH_SHORT).show()
  }
  
  private fun showInvalidPasswordWarning() {
    binding.passwordTextField.error = "Invalid password"
    Toast.makeText(this, "Please follow the password creation guidelines", Toast.LENGTH_SHORT).show()
  }
  
  private fun showPasswordMismatchWarning() {
    Toast.makeText(this, "There's a mismatch between your passwords", Toast.LENGTH_SHORT).show()
  }

  private fun setNewUserProfile() {
    val request = userProfileChangeRequest {
      displayName = "${binding.nameTextField.editText?.text.toString()} ${binding.lastNameTextField.editText?.text.toString()}"
    }
    UserSessionManager.getCurrentUser()?.updateProfile(request)?.addOnSuccessListener {
      Toast.makeText(this, "User has now a name", Toast.LENGTH_SHORT).show()
    }
  }

  private fun textFieldsAreValid(): RegistrationErrors {
    val name: String = binding.nameTextField.editText?.text.toString()
    val lastName: String = binding.lastNameTextField.editText?.text.toString()
    val email: String = binding.userTextField.editText?.text.toString()
    val password: String = binding.passwordTextField.editText?.text.toString()
    val passwordConfirmation: String = binding.confirmPasswordTextField.editText?.text.toString()
    if (email.isEmpty() or password.isEmpty() or name.isEmpty() or lastName.isEmpty()) {
      return RegistrationErrors.EMPTY_FIELDS
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      return RegistrationErrors.INVALID_EMAIL
    }
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}\$")
    if (!password.matches(regex)) {
      return RegistrationErrors.INVALID_PASSWORD
    }
    if (password != passwordConfirmation) {
      return RegistrationErrors.PASSWORD_MISMATCH
    }
    return RegistrationErrors.NONE
  }
}