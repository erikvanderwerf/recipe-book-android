package com.gmail.eski787.recipebook.repo.dev

import com.gmail.eski787.recipebook.data.Metadata

class DevSource(
    override val type: String,
    override val url: String,
    override val author: String,
) : Metadata.Source {
    class Factory {
        lateinit var type: String
        lateinit var url: String
        lateinit var author: String
        fun build() = DevSource(type, url, author)
    }
}