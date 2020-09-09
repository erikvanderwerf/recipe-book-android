package com.gmail.eski787.recipebook.ui.index

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.IndexedItem
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [IndexedItem].
 */
class IndexedItemRecyclerViewAdapter() : RecyclerView.Adapter<IndexedItemRecyclerViewAdapter.ViewHolder>() {
    private var items: List<IndexedItem> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_index, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.idView.text = position.toString()
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<IndexedItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}