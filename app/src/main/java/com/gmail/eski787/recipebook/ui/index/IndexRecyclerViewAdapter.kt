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
                currentIndexedItem?.let { item -> fragment.onClickIndex(item.identity) }
            }
        }

        private var currentIndexedItem: IndexedItem? = null
        private val ordinalTextView = view.findViewById<TextView>(R.id.tv_ordinal)
        private val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        private val identityTextView = view.findViewById<TextView>(R.id.tv_identity)
        private val versionTextView = view.findViewById<TextView>(R.id.tv_version)

        override fun toString(): String {
            return super.toString() + " '" + titleTextView.text + "'"
        }

        fun setCurrentItem(position: Int, item: IndexedItem) {
            currentIndexedItem = item
            ordinalTextView.text = position.toString()
            titleTextView.text = item.name
            identityTextView.text = item.identity.dot()
            versionTextView.text = item.version
        }
    }
}