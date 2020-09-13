package com.gmail.eski787.recipebook.repo

sealed class Result <out R> {
    data class Loading(val status: String) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}