package com.gmail.eski787.recipebook.repo

import com.gmail.eski787.recipebook.data.IndexedItem

class MergedRecipeRepository(private val repositories: List<RecipeRepository>) : RecipeRepository {
    override val name = "merged"

    override fun fetchIndex(): Result<List<IndexedItem>> {
        val combined = ArrayList<IndexedItem>()
        for (repo in repositories) {
            when (val result = repo.fetchIndex()) {
                is Result.Success -> combined.addAll(result.data)
                is Result.Error -> return result
            }
        }
        return Result.Success(combined)
    }
}
