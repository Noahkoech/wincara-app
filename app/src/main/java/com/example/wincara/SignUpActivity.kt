package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Create and start the intent here
            val intent = Intent(this@SignUpActivity, LoginActivity2::class.java)
            startActivity(intent)

        }
    }
}