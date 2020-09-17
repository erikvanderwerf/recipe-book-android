package com.gmail.eski787.recipebook.repo

import java.io.InputStream
import java.util.stream.Collectors

class HttpException(val code: Int, inputStream: InputStream?) :
    Exception("HTTP request returned $code") {
    val body: String = if (inputStream != null)
        inputStream.bufferedReader().lines().collect(Collectors.joining())
    else "No Body"
}