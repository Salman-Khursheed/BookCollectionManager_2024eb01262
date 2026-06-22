package com.example.bookcollectionmanager_2024eb01262

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class BookListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book_list)

        val listView =
            findViewById<ListView>(R.id.listViewBooks)

        val dbHelper =
            DatabaseHelper(this)

        val books =
            dbHelper.getAllBooks()

        val adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                books
            )

        listView.adapter = adapter
    }
}