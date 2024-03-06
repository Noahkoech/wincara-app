package com.example.wincara

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wincara.registration.database.MyDatabaseHelper2

class LoginActivity2 : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        dbHelper = MyDatabaseHelper2(this)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            // Retrieve input data
            val username = findViewById<EditText>(R.id.username_input).text.toString().trim()
            val password = findViewById<EditText>(R.id.password_input).text.toString()

            // Check if any fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                // Show an error message indicating that both fields are required
                Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Query the database to check if the provided username and password match any user record
            val user = dbHelper.getUserByUsernameAndPassword(username, password)

            // Check if a user with the provided username and password was found
            if (user != null) {
                // Login successful, navigate to the home activity
                val intent = Intent(this@LoginActivity2, UserListActivity::class.java)
                startActivity(intent)
                // Optionally, you can display a success message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // Finish this activity to prevent the user from coming back to the login screen
                finish()
            } else {
                // Login failed, show an error message indicating that the provided credentials are incorrect
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
            }
        }

        val signUpButton = findViewById<Button>(R.id.button)
        signUpButton.setOnClickListener {
            // Navigate to the sign-up activity
            val intent = Intent(this@LoginActivity2, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
