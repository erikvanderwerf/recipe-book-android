package com.gmail.eski787.recipebook.data

class OpenRecipeIdentifier private constructor(private val parts: Array<String>) {
    companion object {
        fun fromDot(dotFormat: String): OpenRecipeIdentifier {
            return OpenRecipeIdentifier(dotFormat.split(".").toTypedArray())
        }

        fun fromPath(pathFormat: String): OpenRecipeIdentifier {
            return OpenRecipeIdentifier(pathFormat.split("/").toTypedArray())
        }
    }

    override fun toString(): String = dot();

    fun dot(): String = parts.joinToString(".")

    fun path(): String = parts.joinToString("/")
}