package com.example.bookcollectionmanager_2024eb01262

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val etTitle =
            findViewById<EditText>(R.id.etTitle)

        val etAuthor =
            findViewById<EditText>(R.id.etAuthor)

        val btnSave =
            findViewById<Button>(R.id.btnSave)

        val btnView =
            findViewById<Button>(R.id.btnView)

        btnSave.setOnClickListener {

            val title =
                etTitle.text.toString()

            val author =
                etAuthor.text.toString()

            if (title.isNotEmpty() &&
                author.isNotEmpty()
            ) {

                val inserted =
                    dbHelper.insertBook(
                        title,
                        author
                    )

                if (inserted) {

                    Toast.makeText(
                        this,
                        "Book Saved Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    etTitle.text.clear()
                    etAuthor.text.clear()
                }
            }
        }

        btnView.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookListActivity::class.java
                )

            startActivity(intent)
        }
    }
}