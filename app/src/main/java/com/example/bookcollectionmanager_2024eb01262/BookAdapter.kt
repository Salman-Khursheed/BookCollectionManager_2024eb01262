package com.example.bookcollectionmanager_2024eb01262

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private val books: MutableList<Book>,
    private val onDeleteClick: (book: Book, position: Int) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:  TextView    = itemView.findViewById(R.id.tvBookTitle)
        val tvAuthor: TextView    = itemView.findViewById(R.id.tvBookAuthor)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.tvTitle.text  = book.title
        holder.tvAuthor.text = book.author

        holder.btnDelete.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_ID.toInt()) {
                onDeleteClick(books[adapterPosition], adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int = books.size

    fun removeAt(position: Int) {
        books.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, books.size)
    }
}
