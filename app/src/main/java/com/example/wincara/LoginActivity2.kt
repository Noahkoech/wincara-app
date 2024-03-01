package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Create and start the intent here
            val intent = Intent(this@LoginActivity2, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}