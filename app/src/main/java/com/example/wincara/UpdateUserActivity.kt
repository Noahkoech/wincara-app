package com.example.wincara

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wincara.registration.database.MyDatabaseHelper2

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var departmentEditText: EditText
    private lateinit var updateUserButton: Button

    private lateinit var dbHelper: MyDatabaseHelper2
    private lateinit var db: SQLiteDatabase
    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        genderEditText = findViewById(R.id.genderEditText)
        departmentEditText = findViewById(R.id.departmentEditText)
        updateUserButton = findViewById(R.id.updateUserButton)

        // Get user id passed from UserListActivity
        userId = intent.getLongExtra("USER_ID", -1)

        dbHelper = MyDatabaseHelper2(this)
        db = dbHelper.writableDatabase

        // Load user data into EditText fields
        loadUserData()

        // Set click listener for Update User button
        updateUserButton.setOnClickListener {
            updateUser()
        }
    }

    private fun loadUserData() {
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${MyDatabaseHelper2.TABLE_NAME} WHERE ${MyDatabaseHelper2.COLUMN_ID} = ?", arrayOf(userId.toString()))
        if (cursor.moveToFirst()) {
            firstNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_FIRST_NAME)))
            lastNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_LAST_NAME)))
            usernameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_USERNAME)))
            passwordEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_PASSWORD)))
            genderEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_GENDER)))
            departmentEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_DEPARTMENT)))
        }
        cursor.close()
    }

    private fun updateUser() {
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val gender = genderEditText.text.toString()
        val department = departmentEditText.text.toString()

        val values = ContentValues().apply {
            put(MyDatabaseHelper2.COLUMN_FIRST_NAME, firstName)
            put(MyDatabaseHelper2.COLUMN_LAST_NAME, lastName)
            put(MyDatabaseHelper2.COLUMN_USERNAME, username)
            put(MyDatabaseHelper2.COLUMN_PASSWORD, password)
            put(MyDatabaseHelper2.COLUMN_GENDER, gender)
            put(MyDatabaseHelper2.COLUMN_DEPARTMENT, department)
        }

        val selection = "${MyDatabaseHelper2.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(userId.toString())

        val rowsAffected = db.update(MyDatabaseHelper2.TABLE_NAME, values, selection, selectionArgs)
        if (rowsAffected > 0) {
            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@UpdateUserActivity, UserListActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
