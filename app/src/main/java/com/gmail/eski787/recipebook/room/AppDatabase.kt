package com.gmail.eski787.recipebook.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.eski787.recipebook.room.repoconfig.RepoConfigDao
import com.gmail.eski787.recipebook.room.repoconfig.RepoConfigEntity

@Database(entities = [RepoConfigEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoConfigDao(): RepoConfigDao
}