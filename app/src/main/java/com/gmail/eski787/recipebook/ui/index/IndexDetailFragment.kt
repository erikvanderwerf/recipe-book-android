package com.gmail.eski787.recipebook.ui.index

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

class IndexDetailFragment : Fragment() {
    companion object {
        const val TAG = "IndexDetailFragment"
        private const val ARG_IDENTIFIER = "identifier"

        fun newInstance(identifier: OpenRecipeIdentifier): IndexDetailFragment {
            val args = Bundle()
            args.putString(ARG_IDENTIFIER, identifier)

            val indexDetailFragment = IndexDetailFragment()
            indexDetailFragment.arguments = args
            return indexDetailFragment
        }
    }

    private lateinit var viewModel: IndexDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.index_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IndexDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}