package com.gmail.eski787.recipebook.ui.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.Result

class MetadataFragment : Fragment() {
    companion object {
        const val TAG = "MetadataFragment"
        private const val ARG_IDENTIFIER = "identifier"

        fun newInstance(identifier: OpenRecipeIdentifier): MetadataFragment {
            val args = Bundle()
            args.putString(ARG_IDENTIFIER, identifier.dot())

            val indexDetailFragment = MetadataFragment()
            indexDetailFragment.arguments = args
            return indexDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.index_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model by viewModels<MetadataViewModel>()
        arguments?.also { arg ->
            arg.getString(ARG_IDENTIFIER)?.let { it ->
                model.collectMetadataFor(OpenRecipeIdentifier.fromDot(it))
                    .observe(viewLifecycleOwner, {
                        when (it) {
                            is Result.Loading -> displayLoading(it.status)
                            is Result.Success -> displayMetadata(it.data)
                            is Result.Error -> displayError(it.exception)
                        }
                    })
            }
        }
    }

    private fun displayLoading(status: String) {
        view?.findViewById<TextView>(R.id.tv_loading)?.apply {
            visibility = View.VISIBLE
            text = status
        }
    }

    private fun displayMetadata(data: Metadata) {
        view?.let {
            it.findViewById<TextView>(R.id.tv_loading)?.visibility = View.GONE
            it.findViewById<TextView>(R.id.tv_title).text = data.name
            it.findViewById<TextView>(R.id.tv_type).text = data.type
            it.findViewById<TextView>(R.id.tv_identity).text = data.identity.dot()
            it.findViewById<TextView>(R.id.tv_version).text = data.version
            it.findViewById<TextView>(R.id.tv_lang).text = data.lang
        }
    }

    private fun displayError(exception: Exception) {
        view?.findViewById<TextView>(R.id.tv_loading)?.apply {
            visibility = View.VISIBLE
            text = exception.message
        }
    }
}