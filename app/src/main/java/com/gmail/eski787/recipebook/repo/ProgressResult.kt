package com.gmail.eski787.recipebook.repo

/**
 * Status of a result.
 *
 * TODO Split into BinaryResult and ProgressResult
 */
sealed class ProgressResult<out R> {
    data class Loading(val status: String) : ProgressResult<Nothing>()
    data class Success<out T>(val data: T) : ProgressResult<T>()
    data class Error(val exception: Exception) : ProgressResult<Nothing>()
}