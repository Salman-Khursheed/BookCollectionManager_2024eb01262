package com.example.bookcollectionmanager_2024eb01262

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Book(
    val id: Long,
    val title: String,
    val author: String
)

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME    = "BooksDB"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Books"
        const val COL_ID     = "id"
        const val COL_TITLE  = "title"
        const val COL_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $COL_ID    INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT NOT NULL,
                $COL_AUTHOR TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /** Insert a book. Returns true on success. */
    fun insertBook(title: String, author: String): Boolean {
        val values = ContentValues().apply {
            put(COL_TITLE,  title.trim())
            put(COL_AUTHOR, author.trim())
        }
        val result = writableDatabase.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    /** Return all books ordered by newest first. */
    fun getAllBooks(): List<Book> {
        val list = mutableListOf<Book>()
        val cursor = readableDatabase.rawQuery(
            "SELECT $COL_ID, $COL_TITLE, $COL_AUTHOR FROM $TABLE_NAME ORDER BY $COL_ID DESC",
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                list.add(
                    Book(
                        id     = it.getLong(it.getColumnIndexOrThrow(COL_ID)),
                        title  = it.getString(it.getColumnIndexOrThrow(COL_TITLE)),
                        author = it.getString(it.getColumnIndexOrThrow(COL_AUTHOR))
                    )
                )
            }
        }
        return list
    }

    /** Delete a book by its ID. Returns true if a row was deleted. */
    fun deleteBook(id: Long): Boolean {
        val rows = writableDatabase.delete(
            TABLE_NAME,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        return rows > 0
    }
}
