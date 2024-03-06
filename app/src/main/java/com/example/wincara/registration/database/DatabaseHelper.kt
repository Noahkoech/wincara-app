package com.example.wincara.registration.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.wincara.User

class MyDatabaseHelper2(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun insertUser(firstName: String, lastName: String, username: String, password: String, gender: String, department: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, firstName)
            put(COLUMN_LAST_NAME, lastName)
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_GENDER, gender)
            put(COLUMN_DEPARTMENT, department)
        }
        val newRowId = db.insert(TABLE_NAME, null, values)
        db.close()
        return newRowId
    }

    fun getUserByUsernameAndPassword(username: String, password: String): User? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_GENDER, COLUMN_DEPARTMENT)
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            val department = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT))
            user = User(userId, firstName, lastName, username, password, gender, department)
        }
        cursor.close()
        db.close()
        return user
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_GENDER, COLUMN_DEPARTMENT)
        val cursor = db.query(TABLE_NAME, projection, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val userId = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val firstName = getString(getColumnIndexOrThrow(COLUMN_FIRST_NAME))
                val lastName = getString(getColumnIndexOrThrow(COLUMN_LAST_NAME))
                val username = getString(getColumnIndexOrThrow(COLUMN_USERNAME))
                val password = getString(getColumnIndexOrThrow(COLUMN_PASSWORD))
                val gender = getString(getColumnIndexOrThrow(COLUMN_GENDER))
                val department = getString(getColumnIndexOrThrow(COLUMN_DEPARTMENT))
                userList.add(User(userId, firstName, lastName, username, password, gender, department))
            }
        }

        cursor.close()
        db.close()
        return userList
    }

    fun deleteUser(userId: Long) {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(userId.toString())
        db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyDatabase.db"
        const val TABLE_NAME = "Users"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_DEPARTMENT = "department"
        private const val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_FIRST_NAME TEXT, $COLUMN_LAST_NAME TEXT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT, $COLUMN_GENDER TEXT, $COLUMN_DEPARTMENT TEXT)"
        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}