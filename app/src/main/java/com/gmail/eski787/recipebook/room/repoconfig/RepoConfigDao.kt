package com.gmail.eski787.recipebook.room.repoconfig

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RepoConfigDao {
    @Query("SELECT * FROM repo_configs")
    fun getAllRepos(): List<RepoConfigEntity>

    @Insert
    fun addRepo(repoConfig: RepoConfigEntity)

    @Delete
    fun deleteRepo(repoConfig: RepoConfigEntity)
}