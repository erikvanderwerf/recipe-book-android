package com.gmail.eski787.recipebook.data

interface Metadata {
    val name: String
    val type: String
    val identity: OpenRecipeIdentifier
    val version: String
    val lang: String
    val source: List<Source>

    interface Source {
        val type: String
        val url: String
        val author: String
    }
}