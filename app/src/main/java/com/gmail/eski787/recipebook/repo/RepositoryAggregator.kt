package com.gmail.eski787.recipebook.repo

/**
 * RecipeBook supports adding custom repository definitions. They must all be collected into a
 * common location to be able to effectively search for something.
 */
interface RepositoryAggregator {
    fun getRepositories(): List<RecipeRepository>

    fun merge(): RecipeRepository {
        return MergedRecipeRepository(getRepositories())
    }
}