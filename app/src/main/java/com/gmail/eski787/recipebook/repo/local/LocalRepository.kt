package com.gmail.eski787.recipebook.repo.local

import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult
import com.gmail.eski787.recipebook.repo.RecipeRepository

object LocalRepository : RecipeRepository {
    override val name: String = "local"

    override fun getIndex(): ProgressResult<List<IndexedItem>> {
        TODO("Not yet implemented")
    }

    override fun getMetadataFor(identifier: OpenRecipeIdentifier): ProgressResult<Metadata> {
        TODO("Not yet implemented")
    }
}