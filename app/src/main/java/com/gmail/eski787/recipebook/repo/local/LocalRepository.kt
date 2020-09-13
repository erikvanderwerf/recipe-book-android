package com.gmail.eski787.recipebook.repo.local

import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.RecipeRepository
import com.gmail.eski787.recipebook.repo.Result

object LocalRepository : RecipeRepository {
    override val name: String = "local"

    override fun getIndex(): Result<List<IndexedItem>> {
        TODO("Not yet implemented")
    }

    override fun getMetadataFor(identifier: OpenRecipeIdentifier): Result<Metadata> {
        TODO("Not yet implemented")
    }
}