package com.gmail.eski787.recipebook.repo.dev

import android.net.Uri
import android.util.JsonReader
import android.util.Log
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.RecipeRepository
import com.gmail.eski787.recipebook.repo.Result
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class DevRepository(override val name: String, private val uri: Uri) : RecipeRepository {
    companion object {
        const val TAG = "DevRepository"

        val EMULATOR_HOST = DevRepository("EmulatorHost", Uri.parse("http://10.0.2.2:5000"))
    }

    override fun fetchIndex(): Result<List<IndexedItem>> {
        Log.i(TAG, "Start Fetch Index")
        val indexUrl = URL(uri.buildUpon().appendEncodedPath( "index").build().toString())

        (indexUrl.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            connectTimeout = 5 * 1000
            setRequestProperty("Accept", "application/json")
            return try {
                Result.Success(collectIndexItems(inputStream))
            } catch (e: IOException) {
                Result.Error(e)
            }
        }
        return Result.Error(RuntimeException("Could not connect to dev repo."))
    }

    private fun collectIndexItems(inputStream: InputStream): List<IndexedItem> {
        val itemsList = ArrayList<IndexedItem>()
        val reader = JsonReader(InputStreamReader(inputStream))
        reader.beginArray()
        while(reader.hasNext()) {
            val name = reader.nextString()
            val item = IndexedItem(name, name)
            itemsList.add(item)
        }
        reader.endArray()
        reader.close()
        inputStream.close()
        return itemsList
    }
}