package com.gmail.eski787.recipebook.ui.index

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.eski787.recipebook.R
import java.lang.Exception

/**
 * A fragment representing a list of Items.
 */
class IndexListFragment : Fragment() {
    companion object {
        const val TAG = "IndexListFragment"

        @JvmStatic
        fun newInstance() = IndexListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_index_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view !is RecyclerView) throw RuntimeException("IndexListFragment was instantiated " +
                "with the wrong view. Got $view")

        val recyclerViewAdapter = IndexedItemRecyclerViewAdapter()
        val model: IndexedItemViewModel by viewModels()
        model.getIndexedItems().observe(viewLifecycleOwner, {
            recyclerViewAdapter.setItems(it)
        })
        model.getFailureStatus().observe(viewLifecycleOwner, {
            Log.e(TAG, "Could not collect index.", it)
            // TODO Display error to GUI
        })

        // Set the adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }
}