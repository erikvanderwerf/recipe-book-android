package com.gmail.eski787.recipebook.repo

import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

class ItemNotFoundException(identifier: OpenRecipeIdentifier) :
    Exception("Could not find item in repo: $identifier")
