package com.example.bookcollectionmanager_2024eb01262

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListActivity : AppCompatActivity() {

    private lateinit var dbHelper:     DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutEmpty:  LinearLayout
    private lateinit var tvBookCount:  TextView
    private lateinit var adapter:      BookAdapter

    private val bookList = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        dbHelper    = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewBooks)
        layoutEmpty  = findViewById(R.id.layoutEmpty)
        tvBookCount  = findViewById(R.id.tvBookCount)

        adapter = BookAdapter(bookList) { book, position ->
            confirmDelete(book, position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter       = adapter

        loadBooks()
    }

    private fun loadBooks() {
        bookList.clear()
        bookList.addAll(dbHelper.getAllBooks())
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        val count = bookList.size
        if (count == 0) {
            recyclerView.visibility = View.GONE
            layoutEmpty.visibility  = View.VISIBLE
            tvBookCount.text = "0 books"
        } else {
            recyclerView.visibility = View.VISIBLE
            layoutEmpty.visibility  = View.GONE
            tvBookCount.text = "$count ${if (count == 1) "book" else "books"}"
        }
    }

    private fun confirmDelete(book: Book, position: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_confirm_title))
            .setMessage("Remove \"${book.title}\" by ${book.author}?")
            .setPositiveButton(getString(R.string.delete_confirm_yes)) { _, _ ->
                val deleted = dbHelper.deleteBook(book.id)
                if (deleted) {
                    adapter.removeAt(position)
                    updateEmptyState()
                    android.widget.Toast.makeText(
                        this,
                        getString(R.string.toast_deleted),
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.delete_confirm_no), null)
            .show()
    }
}
