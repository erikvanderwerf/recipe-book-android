package com.gmail.eski787.recipebook.repo

import android.net.Uri
import com.gmail.eski787.recipebook.data.IndexedItem

class LocalRepository: RecipeRepository {
    override val name: String
        get() = TODO("Not yet implemented")

    override fun fetchIndex(): Result<List<IndexedItem>> {
        TODO("Not yet implemented")
    }
}