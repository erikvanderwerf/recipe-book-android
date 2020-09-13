package com.gmail.eski787.recipebook

import android.app.Application
import androidx.room.Room
import com.gmail.eski787.recipebook.repo.RoomRepositoryAggregator
import com.gmail.eski787.recipebook.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeBookApplication : Application(), CoroutineScope {
    companion object {
        const val TAG = "RecipeBookApplication"

        lateinit var roomDatabase: AppDatabase
    }

    override val coroutineContext = Dispatchers.IO

    override fun onCreate() {
        super.onCreate()

        roomDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database")
                .fallbackToDestructiveMigration()
                .build()

        // TODO Prepopulate with asset file
        launch {
            RoomRepositoryAggregator.prepopulateRepoConfigs()
        }
    }
}