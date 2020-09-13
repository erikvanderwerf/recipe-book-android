package com.gmail.eski787.recipebook.repo

import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

/**
 * Interface for accessing a read-only Open Recipe Repository.
 */
interface RecipeRepository {
    val name: String

    /**
     * Get an index of every item hosted by the repository.
     */
    fun getIndex(): Result<List<IndexedItem>>

    /**
     * Get the metadata for the item signified by the given identifier.
     *
     * @throws ItemNotFoundException if the repository could not find the given identifier.
     */
    fun getMetadataFor(identifier: OpenRecipeIdentifier): Result<Metadata>
}