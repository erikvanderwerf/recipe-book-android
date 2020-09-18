package com.gmail.eski787.recipebook.ui.dev

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.ui.DevActivity


/**
 * A simple [Fragment] subclass.
 * Use the [IndexConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IndexConfirmFragment : Fragment() {
    companion object {
        const val TAG = "IndexConfirmFragment"
        private const val ARG_ERROR_STRING = "error_string"

        @JvmStatic
        fun newInstance() = IndexConfirmFragment()

        @JvmStatic
        fun newInstance(message: String): IndexConfirmFragment {
            val args = Bundle()
            args.putString(ARG_ERROR_STRING, message)

            val icf = IndexConfirmFragment()
            icf.arguments = args
            return icf
        }
    }

    private var devActivity: DevActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DevActivity) {
            devActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_index_confirm, container, false)
        val button = view.findViewById<Button>(R.id.b_index)
        button.setOnClickListener { devActivity?.confirm() }

        savedInstanceState ?: arguments ?.let { bundle ->
            bundle.getString(ARG_ERROR_STRING)?.let { message ->
                view.findViewById<TextView>(R.id.tv_error).text = message
            }
        }

        return view
    }
}