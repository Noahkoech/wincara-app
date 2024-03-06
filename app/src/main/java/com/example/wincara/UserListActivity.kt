package com.example.wincara

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wincara.registration.database.MyDatabaseHelper2

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val dbHelper = MyDatabaseHelper2(this)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${MyDatabaseHelper2.TABLE_NAME}", null)

        val userListLayout: TableLayout = findViewById(R.id.userListTable)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_ID))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_LAST_NAME))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_USERNAME))
                val gender = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_GENDER))
                val department = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper2.COLUMN_DEPARTMENT))

                val tableRow = TableRow(this)

                val idTextView = TextView(this)
                idTextView.text = id.toString()
                tableRow.addView(idTextView)

                val nameTextView = TextView(this)
                nameTextView.text = "$firstName $lastName"
                tableRow.addView(nameTextView)

                val usernameTextView = TextView(this)
                usernameTextView.text = username
                tableRow.addView(usernameTextView)

                val genderTextView = TextView(this)
                genderTextView.text = gender
                tableRow.addView(genderTextView)

                val departmentTextView = TextView(this)
                departmentTextView.text = department
                tableRow.addView(departmentTextView)

                val updateButton = Button(this)
                updateButton.text = "Update"
                updateButton.setOnClickListener {
                    // Handle update button click
                    // You can navigate to an update activity with user id as extra data
                    val intent = Intent(this, UpdateUserActivity::class.java)
                    intent.putExtra("USER_ID", id)
                    startActivity(intent)
                }
                tableRow.addView(updateButton)

                val deleteButton = Button(this)
                deleteButton.text = "Delete"
                deleteButton.setOnClickListener {
                    // Handle delete button click
                    // You can prompt a confirmation dialog before deleting the user
                    dbHelper.deleteUser(id)
                    // Refresh the user list after deletion
                    refreshUserList()
                }
                tableRow.addView(deleteButton)

                userListLayout.addView(tableRow)

            } while (cursor.moveToNext())
        } else {
            // Handle case where no users are found
            val noUserTextView = TextView(this)
            noUserTextView.text = "No users found."
            userListLayout.addView(noUserTextView)
        }

        cursor.close()
        db.close()
    }

    private fun refreshUserList() {
        val intent = intent
        finish()
        startActivity(intent)
    }
}
