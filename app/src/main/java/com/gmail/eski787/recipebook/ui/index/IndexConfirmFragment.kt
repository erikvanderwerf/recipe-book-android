package com.gmail.eski787.recipebook.ui.index

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gmail.eski787.recipebook.ui.IndexActivity
import com.gmail.eski787.recipebook.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IndexConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IndexConfirmFragment : Fragment() {
    private var indexActivity: IndexActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IndexActivity) {
            indexActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_index_confirm, container, false)
        val button = view.findViewById<Button>(R.id.b_index)
        button.setOnClickListener { indexActivity?.confirm() }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment IndexConfirmFragment.
         */
        @JvmStatic
        fun newInstance() = IndexConfirmFragment()
    }
}