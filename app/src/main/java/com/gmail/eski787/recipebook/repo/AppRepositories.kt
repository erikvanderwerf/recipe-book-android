package com.gmail.eski787.recipebook.repo

import android.net.Uri
import android.util.JsonReader
import android.util.JsonWriter
import com.gmail.eski787.recipebook.RecipeBookApplication
import com.gmail.eski787.recipebook.repo.dev.DevRepository
import com.gmail.eski787.recipebook.repo.local.LocalRepository
import com.gmail.eski787.recipebook.room.RepoConfigEntity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

object AppRepositories {
    private const val REPO_TYPE_LOCAL = 0
    private const val REPO_TYPE_DEV = 1

    private const val DEV_NAME = "name"
    private const val DEV_URI = "uri"

    fun prepopulateRepoConfigs() {
        val repoConfigDao = RecipeBookApplication.roomDatabase.repoConfigDao()
        if (repoConfigDao.getAllRepos().isEmpty()) {
            repoConfigDao.addRepo(
                RepoConfigEntity(0, REPO_TYPE_LOCAL, ByteArray(0))
            )

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

    fun getRepositories(): List<RecipeRepository> {
        return RecipeBookApplication.roomDatabase.repoConfigDao().getAllRepos().map {
            when (it.type) {
                REPO_TYPE_LOCAL -> generateLocalRepo()
                REPO_TYPE_DEV -> generateDevRepo(it.blob)
                else -> throw RuntimeException("Got invalid repo type number.")
            }
        }
    }

    fun getCombinedRepositories(): RecipeRepository {
        return MergedRecipeRepository(getRepositories())
    }

    private fun generateLocalRepo(): LocalRepository {
        return LocalRepository()
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