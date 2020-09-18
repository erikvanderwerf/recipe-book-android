package com.gmail.eski787.recipebook.repo

import android.util.Log
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Item
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

class MergedRecipeRepository(private val repositories: List<RecipeRepository>) : RecipeRepository {
    companion object {
        const val TAG = "MergedRecipeRepository"
    }

    override val name = "merged"

    override fun getIndex(): ProgressResult<List<IndexedItem>> {
        val combined = ArrayList<IndexedItem>()
        for (repo in repositories) {
            when (val result = repo.getIndex()) {
                is ProgressResult.Success -> combined.addAll(result.data)
                is ProgressResult.Error -> return result
            }
        }
        return ProgressResult.Success(combined)
    }

    override fun getMetadataFor(identifier: OpenRecipeIdentifier): ProgressResult<Metadata> {
        for (repo in repositories) {
            when (val result = repo.getMetadataFor(identifier)) {
                is ProgressResult.Success -> return result
                is ProgressResult.Error -> Log.i(TAG, "repo returned error: ${result.exception}")
            }
        }
        return ProgressResult.Error(ItemNotFoundException(identifier))
    }

    override fun getItem(identifier: OpenRecipeIdentifier): ProgressResult<Item> {
        TODO("Not yet implemented")
    }
}
