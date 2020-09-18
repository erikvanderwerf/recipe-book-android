package com.gmail.eski787.recipebook.ui.dev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult

class DetailsFragment : Fragment() {
    companion object {
        const val TAG = "DetailsFragment"
        private const val ARG_IDENTIFIER = "identifier"

        fun newInstance(identifier: OpenRecipeIdentifier): DetailsFragment {
            val args = Bundle()
            args.putString(ARG_IDENTIFIER, identifier.dot())

            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = args
            return detailsFragment
        }
    }

    private lateinit var identifier: OpenRecipeIdentifier

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model by viewModels<DetailsViewModel>()

        arguments?.let { arg ->
            arg.getString(ARG_IDENTIFIER)?.let { id ->
                identifier = OpenRecipeIdentifier.fromDot(id)
                model.collectDetailsFor(identifier).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is ProgressResult.Loading -> TODO()
                        is ProgressResult.Success -> TODO()
                        is ProgressResult.Error -> TODO()
                    }
                })
            }
        }
    }
}