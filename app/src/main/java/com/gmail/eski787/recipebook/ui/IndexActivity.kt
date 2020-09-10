package com.gmail.eski787.recipebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.ui.index.*

class IndexActivity : AppCompatActivity(), IndexConfirmInterface, IndexListInterface {
    companion object {
        const val TAG = "IndexActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index_activity)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, IndexConfirmFragment.newInstance())
                .commit()
    }

    override fun confirm() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IndexListFragment.newInstance())
            .commit()
    }

    override fun onClick(identifier: OpenRecipeIdentifier) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IndexedItemDetailFragment.newInstance(identifier))
            .addToBackStack("details")
            .commit()
    }

    override fun onError(message: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IndexConfirmFragment.newInstance(message))
            .commit()
    }
}