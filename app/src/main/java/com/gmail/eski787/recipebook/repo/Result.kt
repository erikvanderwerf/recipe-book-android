package com.gmail.eski787.recipebook.repo

/**
 * Status of a reult.
 *
 * TODO Split into Result and ProgressiveResult
 */
sealed class Result <out R> {
    data class Loading(val status: String) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}