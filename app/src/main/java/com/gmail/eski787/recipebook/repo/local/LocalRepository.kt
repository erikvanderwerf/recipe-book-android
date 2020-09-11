package com.gmail.eski787.recipebook.repo.local

import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.repo.RecipeRepository
import com.gmail.eski787.recipebook.repo.Result

class LocalRepository: RecipeRepository {
    override val name: String = "Local"

    override fun fetchIndex(): Result<List<IndexedItem>> {
        return Result.Success(emptyList())
    }
}