package com.gmail.eski787.recipebook.data

interface Metadata {
    val name: String
    val type: String
    val identity: OpenRecipeIdentifier
    val version: String
    val lang: String
}