package com.example.wincara

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wincara.registration.database.MyDatabaseHelper2

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val dbHelper = MyDatabaseHelper2(this)

        val signupButton = findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            // Retrieve input data
            val firstName = findViewById<EditText>(R.id.firstNameEditText).text.toString().trim()
            val lastName = findViewById<EditText>(R.id.lastNameEditText).text.toString().trim()
            val username = findViewById<EditText>(R.id.usernameEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
            val gender = if (findViewById<RadioButton>(R.id.maleRadioButton).isChecked) "Male" else "Female"
            val department = findViewById<EditText>(R.id.departmentEditText).text.toString().trim()

            // Check if any fields are empty
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || department.isEmpty()) {
                // Show an error message indicating that all fields are required
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Check if the password meets certain criteria (e.g., minimum length)
            if (password.length < 6) {
                // Show an error message indicating that the password is too short
                Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert user data into the database
            val newRowId = dbHelper.insertUser(firstName, lastName, username, password, gender, department)

            // Check if registration was successful
            if (newRowId != -1L) {
                // Registration successful, navigate to login activity
                Toast.makeText(this, "User registered successfully. You can now log in.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, LoginActivity2::class.java)
                startActivity(intent)
                finish() // Finish this activity to prevent the user from coming back to the registration screen
            } else {
                // Registration failed, handle appropriately
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Navigate to the login activity
            val intent = Intent(this@SignUpActivity, LoginActivity2::class.java)
            startActivity(intent)
        }
    }
}
