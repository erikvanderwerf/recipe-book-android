package com.gmail.eski787.recipebook.repo.dev

import android.net.Uri
import android.util.JsonReader
import android.util.Log
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.HttpCodeException
import com.gmail.eski787.recipebook.repo.RecipeRepository
import com.gmail.eski787.recipebook.repo.Result
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class DevRepository(override val name: String, private val uri: Uri) : RecipeRepository {
    companion object {
        const val TAG = "DevRepository"
    }

    override fun getIndex(): Result<List<IndexedItem>> {
        Log.d(TAG, "Fetch index for $name")
        val indexUrl = URL(uri.buildUpon().appendEncodedPath("index").build().toString())
        val itemsList = ArrayList<IndexedItem>()

        val reader: JsonReader
        try {
            reader = getJsonReaderFrom(indexUrl)
        } catch (e: Exception) {
            when (e) {
                is HttpCodeException, is IOException -> return Result.Error(e)
                else -> throw e
            }
        }
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            var id: String? = null;
            var name: String? = null;
            var version: String? = null
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "id" -> id = reader.nextString()
                    "name" -> name = reader.nextString()
                    "ver" -> version = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            val item = IndexedItem(OpenRecipeIdentifier.fromDot(id!!), name!!, version!!)
            itemsList.add(item)
            reader.endObject()
        }
        reader.endArray()
        reader.close()
        return Result.Success(itemsList)
    }

    override fun getMetadataFor(identifier: OpenRecipeIdentifier): Result<Metadata> {
        Log.d(TAG, "Fetch metadata for $identifier")
        val metaUrl = URL(
            uri.buildUpon().appendEncodedPath("meta")
                .appendEncodedPath(identifier.path()).build().toString()
        )
        val reader: JsonReader
        try {
            reader = getJsonReaderFrom(metaUrl)
        } catch (e: Exception) {
            when (e) {
                is HttpCodeException, is IOException -> return Result.Error(e)
                else -> throw e
            }
        }
        val metaFac = DevMetadata.Factory()
        reader.beginObject()
        while (reader.hasNext()) {
            when (val nn = reader.nextName()) {
                "id" -> metaFac.identity = OpenRecipeIdentifier.fromDot(reader.nextString())
                "ver" -> metaFac.version = reader.nextString()
                "name" -> metaFac.name = reader.nextString()
                "type" -> metaFac.type = reader.nextString()
                "lang" -> metaFac.lang = reader.nextString()
                "source" -> metaFac.source = readSource(reader)
                else -> throw RuntimeException("Invalid Metadata Parameter: $nn")
            }
        }
        reader.endObject()
        reader.close()
        return Result.Success(metaFac.build())
    }

    private fun readSource(reader: JsonReader): List<Metadata.Source> {
        val sources = ArrayList<Metadata.Source>()
        reader.beginArray()
        while (reader.hasNext()) {
            val srcFac = DevSource.Factory()
            reader.beginObject()
            while (reader.hasNext()) {
                when (val nn = reader.nextName()) {
                    "type" -> srcFac.type = reader.nextString()
                    "url" -> srcFac.url = reader.nextString()
                    "author" -> srcFac.author = reader.nextString()
                    else -> throw RuntimeException("Invalid Source Parameter: $nn")
                }
            }
            reader.endObject()
            sources.add(srcFac.build())
        }
        reader.endArray()
        return sources
    }

    private fun getJsonReaderFrom(url: URL): JsonReader =
        (url.openConnection() as HttpURLConnection).run {
            requestMethod = "GET"
            connectTimeout = 5 * 1000
            setRequestProperty("Accept", "application/json")
            when (responseCode) {
                200 -> JsonReader(InputStreamReader(inputStream))
                else -> throw HttpCodeException(responseCode, errorStream)
            }
        }
}