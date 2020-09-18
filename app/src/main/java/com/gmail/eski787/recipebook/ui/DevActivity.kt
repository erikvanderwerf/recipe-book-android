package com.gmail.eski787.recipebook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.ui.dev.*

class DevActivity : AppCompatActivity(), IndexConfirmInterface, IndexListInterface,
    MetadataInterface {
    companion object {
        const val TAG = "DevActivity"
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
            .addToBackStack("confirm")
            .commit()
    }

    override fun onClickIndex(identifier: OpenRecipeIdentifier) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MetadataFragment.newInstance(identifier))
            .addToBackStack("metadata")
            .commit()
    }

    override fun onError(message: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IndexConfirmFragment.newInstance(message))
            .commit()
    }

    override fun onDetailsFor(identifier: OpenRecipeIdentifier) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(identifier))
            .addToBackStack("details")
            .commit()
    }
}