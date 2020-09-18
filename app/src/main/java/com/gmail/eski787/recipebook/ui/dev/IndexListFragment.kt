package com.gmail.eski787.recipebook.ui.dev

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult

/**
 * A fragment representing a list of Items.
 */
class IndexListFragment : Fragment(), IndexListInterface {
    companion object {
        const val TAG = "IndexListFragment"

        @JvmStatic
        fun newInstance() = IndexListFragment()
    }

    private var parent: IndexListInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = context as IndexListInterface
    }

    override fun onDetach() {
        super.onDetach()
        parent = null
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

        val recyclerViewAdapter = IndexRecyclerViewAdapter(this)
        val model by viewModels<IndexViewModel>()
        model.collectIndex().observe(viewLifecycleOwner, {
            when (it) {
                is ProgressResult.Loading -> Toast.makeText(this.context,
                    "Loading...",
                    Toast.LENGTH_SHORT)
                    .show()
                is ProgressResult.Success -> recyclerViewAdapter.setItems(it.data)
                is ProgressResult.Error -> {
                    Log.e(TAG, "Could not collect index.", it.exception)
                    parent?.onError(it.exception.message ?: "Unknown Error")
                }
            }
        })

        // Set the adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }

    override fun onClickIndex(identifier: OpenRecipeIdentifier) {
        parent?.onClickIndex(identifier)
    }

    override fun onError(message: String) {
        parent?.onError(message)
    }
}