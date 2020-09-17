package com.gmail.eski787.recipebook.repo

import android.util.Log
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

class MergedRecipeRepository(private val repositories: List<RecipeRepository>) : RecipeRepository {
    companion object {
        const val TAG = "MergedRecipeRepository"
    }

    override val name = "merged"

    override fun getIndex(): Result<List<IndexedItem>> {
        val combined = ArrayList<IndexedItem>()
        for (repo in repositories) {
            when (val result = repo.getIndex()) {
                is Result.Success -> combined.addAll(result.data)
                is Result.Error -> return result
            }
        }
        return Result.Success(combined)
    }

    override fun getMetadataFor(identifier: OpenRecipeIdentifier): Result<Metadata> {
        for (repo in repositories) {
            when (val result = repo.getMetadataFor(identifier)) {
                is Result.Success -> return result
                is Result.Error -> Log.i(TAG, "repo returned error: ${result.exception}")
            }
        }
        return Result.Error(ItemNotFoundException(identifier))
    }
}
