package com.example.bookcollectionmanager_2024eb01262

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "BooksDB"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Books"

        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT,
                $COL_AUTHOR TEXT
            )
        """

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        onCreate(db)
    }

    fun insertBook(
        title: String,
        author: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COL_TITLE, title)
        values.put(COL_AUTHOR, author)

        val result =
            db.insert(TABLE_NAME, null, values)

        return result != -1L
    }

    fun getAllBooks(): ArrayList<String> {

        val list = ArrayList<String>()

        val db = readableDatabase

        val cursor =
            db.rawQuery(
                "SELECT * FROM $TABLE_NAME",
                null
            )

        while (cursor.moveToNext()) {

            val title =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_TITLE)
                )

            val author =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_AUTHOR)
                )

            list.add("$title - $author")
        }

        cursor.close()

        return list
    }
}