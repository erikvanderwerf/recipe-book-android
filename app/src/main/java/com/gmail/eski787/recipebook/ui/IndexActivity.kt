package com.gmail.eski787.recipebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gmail.eski787.recipebook.R
import com.gmail.eski787.recipebook.ui.index.IndexConfirmFragment
import com.gmail.eski787.recipebook.ui.index.IndexFragmentHolder
import com.gmail.eski787.recipebook.ui.index.IndexListFragment

class IndexActivity : AppCompatActivity(), IndexFragmentHolder {
    companion object {
        const val TAG = "IndexActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, IndexConfirmFragment.newInstance())
                    .commitNow()
        }
    }

    override fun confirm() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IndexListFragment.newInstance())
            .commitNow()
    }
}