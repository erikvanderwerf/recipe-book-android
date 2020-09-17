package com.gmail.eski787.recipebook.repo.dev

import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier

class DevMetadata(
    override val name: String,
    override val type: String,
    override val identity: OpenRecipeIdentifier,
    override val version: String,
    override val lang: String,
    override val source: List<Metadata.Source>,
) : Metadata {
    class Factory {
        lateinit var name: String
        lateinit var type: String
        lateinit var identity: OpenRecipeIdentifier
        lateinit var version: String
        lateinit var lang: String
        lateinit var source: List<Metadata.Source>
        fun build() = DevMetadata(name, type, identity, version, lang, source)
    }
}