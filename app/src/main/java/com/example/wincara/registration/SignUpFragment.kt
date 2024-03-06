package com.example.wincara.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wincara.LoginActivity2
import com.example.wincara.R
import com.example.wincara.registration.database.MyDatabaseHelper2

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val dbHelper = MyDatabaseHelper2(this)

        val signupButton = findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            val firstName = findViewById<EditText>(R.id.firstNameEditText).text.toString()
            val lastName = findViewById<EditText>(R.id.lastNameEditText).text.toString()
            val username = findViewById<EditText>(R.id.usernameEditText).text.toString()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
            val gender = if (findViewById<RadioButton>(R.id.maleRadioButton).isChecked) "Male" else "Female"
            val department = findViewById<EditText>(R.id.departmentEditText).text.toString()
            val newRowId = dbHelper.insertUser(firstName, lastName, username, password, gender, department)

            // Check if the newRowId is greater than -1, indicating successful insertion
            if (newRowId > -1) {
                // Data insertion successful
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                // Optionally, navigate to another activity (e.g., login screen)
                startActivity(Intent(this, LoginActivity2::class.java))
                finish() // Finish the current activity to prevent the user from navigating back to the sign-up screen
            } else {
                // Data insertion failed
                Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show()
                // You can handle the failure scenario as needed
            }
        }
    }
}
