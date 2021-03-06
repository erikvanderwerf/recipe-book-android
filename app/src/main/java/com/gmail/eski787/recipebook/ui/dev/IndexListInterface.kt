package com.gmail.eski787.recipebook.ui.dev

import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

interface IndexListInterface {
    /**
     * Called when the user has selected an indexed item.
     *
     * UI should show more details about selected item.
     */
    fun onClickIndex(identifier: OpenRecipeIdentifier)

    /**
     * Called when an index operation resulted in an unrecoverable error.
     *
     * UI may change the fragment to display the error to the user.
     */
    fun onError(message: String)
}