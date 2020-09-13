package com.gmail.eski787.recipebook.repo.dev

import android.net.Uri
import android.util.JsonReader
import android.util.Log
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.HttpException
import com.gmail.eski787.recipebook.repo.ItemNotFoundException
import com.gmail.eski787.recipebook.repo.RecipeRepository
import com.gmail.eski787.recipebook.repo.Result
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
        val reader = getJsonReaderFrom(indexUrl)
        val itemsList = ArrayList<IndexedItem>()

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
        val reader = try {
            getJsonReaderFrom(metaUrl)
        } catch (e: Exception) {
            throw ItemNotFoundException(identifier)
        }
        val metaFac = DevMetadata.Factory()
        reader.beginObject()
        while (reader.hasNext()) {
            val nn = reader.nextName();
            val ns = reader.nextString()
            when (nn) {
                "name" -> metaFac.name = ns
                "type" -> metaFac.type = ns
                "identity" -> metaFac.identity = OpenRecipeIdentifier.fromDot(ns)
                "ver" -> metaFac.version = ns
                "lang" -> metaFac.lang = ns
                else -> throw RuntimeException("Invalid Metadata Parameter")
            }
        }
        reader.endObject()
        reader.close()
        return Result.Success(metaFac.build())
    }

    private fun getJsonReaderFrom(url: URL): JsonReader =
        (url.openConnection() as HttpURLConnection).run {
            requestMethod = "GET"
            connectTimeout = 5 * 1000
            setRequestProperty("Accept", "application/json")
            when (responseCode) {
                200 -> JsonReader(InputStreamReader(inputStream))
                else -> throw HttpException(responseCode)
            }
        }
}