package com.gmail.eski787.recipebook.ui.dev

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult

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

    private lateinit var identifier: OpenRecipeIdentifier
    private var parent: MetadataInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = context as MetadataInterface
    }

    override fun onDetach() {
        super.onDetach()
        parent = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.metadata_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model by viewModels<MetadataViewModel>()
        arguments?.let { arg ->
            arg.getString(ARG_IDENTIFIER)?.let { id ->
                identifier = OpenRecipeIdentifier.fromDot(id)
                model.collectMetadataFor(identifier).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is ProgressResult.Loading -> displayLoading(result.status)
                        is ProgressResult.Success -> displayMetadata(result.data)
                        is ProgressResult.Error -> displayError(result.exception)
                    }
                })
            }
        }

        with(view.findViewById<Button>(R.id.b_download_view)) {
            setOnClickListener { this@MetadataFragment.parent?.onDetailsFor(identifier) }
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
            it.findViewById<Button>(R.id.b_download_view).visibility = View.VISIBLE
        }
    }

    private fun displayError(exception: Exception) {
        view?.findViewById<TextView>(R.id.tv_loading)?.apply {
            visibility = View.VISIBLE
            text = exception.message
        }
    }
}