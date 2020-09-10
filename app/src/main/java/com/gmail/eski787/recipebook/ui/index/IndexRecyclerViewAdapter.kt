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
class IndexRecyclerViewAdapter(val fragment: IndexListInterface) :
    RecyclerView.Adapter<IndexRecyclerViewAdapter.IndexedItemViewHolder>() {

    private var items: List<IndexedItem> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexedItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_index, parent, false)
        return IndexedItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndexedItemViewHolder, position: Int) {
        holder.setCurrentItem(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<IndexedItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class IndexedItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                currentIndexedItem?.let { item -> fragment.onClick(item.identifier) }
            }
        }

        private var currentIndexedItem: IndexedItem? = null
        private val idView: TextView = view.findViewById(R.id.item_number)
        private val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        fun setCurrentItem(position: Int, item: IndexedItem) {
            currentIndexedItem = item
            idView.text = position.toString()
            contentView.text = item.name
        }
    }
}