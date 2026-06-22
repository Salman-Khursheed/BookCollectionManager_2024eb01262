/*
Name: Salman Khursheed
ID: 2024eb01262
*/
package com.example.bookcollectionmanager_2024eb01262


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var etTitle:  EditText
    private lateinit var etAuthor: EditText
    private lateinit var btnSave:  MaterialButton
    private lateinit var btnView:  MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etTitle  = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        btnSave  = findViewById(R.id.btnSave)
        btnView  = findViewById(R.id.btnView)

        btnSave.setOnClickListener { saveBook() }
        btnView.setOnClickListener {
            startActivity(Intent(this, BookListActivity::class.java))
        }
    }

    private fun saveBook() {
        val title  = etTitle.text.toString().trim()
        val author = etAuthor.text.toString().trim()

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_fill_fields), Toast.LENGTH_SHORT).show()
            // Shake the empty field(s)
            if (title.isEmpty())  shakeView(etTitle)
            if (author.isEmpty()) shakeView(etAuthor)
            return
        }

        val inserted = dbHelper.insertBook(title, author)
        if (inserted) {
            Toast.makeText(this, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
            etTitle.text.clear()
            etAuthor.text.clear()
            etTitle.requestFocus()
        }
    }

    /** Small horizontal shake animation to signal a validation error. */
    private fun shakeView(view: View) {
        view.animate()
            .translationX(12f).setDuration(60)
            .withEndAction {
                view.animate().translationX(-12f).setDuration(60)
                    .withEndAction {
                        view.animate().translationX(8f).setDuration(50)
                            .withEndAction {
                                view.animate().translationX(0f).setDuration(50).start()
                            }.start()
                    }.start()
            }.start()
    }
}
