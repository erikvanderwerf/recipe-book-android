package com.gmail.eski787.recipebook.repo

import android.net.Uri
import android.util.JsonReader
import android.util.JsonWriter
import com.gmail.eski787.recipebook.RecipeBookApplication
import com.gmail.eski787.recipebook.repo.dev.DevRepository
import com.gmail.eski787.recipebook.room.repoconfig.RepoConfigEntity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * Implementation of a repository aggregator that uses Android Room libraries.
 */
object RoomRepositoryAggregator : RepositoryAggregator {
    private const val REPO_TYPE_DEV = 0

    private const val DEV_NAME = "name"
    private const val DEV_URI = "uri"

    override fun getRepositories(): List<RecipeRepository> {
        return RecipeBookApplication.roomDatabase.repoConfigDao().getAllRepos().map {
            when (it.type) {
                REPO_TYPE_DEV -> generateDevRepo(it.blob)
                else -> throw RuntimeException("Got invalid repo type number.")
            }
        }
    }

    fun prepopulateRepoConfigs() {
        val repoConfigDao = RecipeBookApplication.roomDatabase.repoConfigDao()
        if (repoConfigDao.getAllRepos().isEmpty()) {
            val bytes = ByteArrayOutputStream()
            JsonWriter(OutputStreamWriter(bytes)).beginObject()
                .name(DEV_NAME).value("EmulatorHost")
                .name(DEV_URI).value("http://10.0.2.2:5000")
                .endObject().close()
            repoConfigDao.addRepo(
                RepoConfigEntity(0, REPO_TYPE_DEV, bytes.toByteArray())
            )
        }
    }

    private fun generateDevRepo(blob: ByteArray): DevRepository {
        var name: String? = null
        var uri: Uri? = null

        val reader = JsonReader(InputStreamReader(ByteArrayInputStream(blob)))
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                DEV_NAME -> name = reader.nextString()
                DEV_URI -> uri = Uri.parse(reader.nextString())
            }
        }
        reader.endObject()
        reader.close()
        return DevRepository(name!!, uri!!)
    }
}