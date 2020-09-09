package com.gmail.eski787.recipebook.repo

import com.gmail.eski787.recipebook.data.IndexedItem

interface RecipeRepository {
    val name: String

    fun fetchIndex(): Result<List<IndexedItem>>
}