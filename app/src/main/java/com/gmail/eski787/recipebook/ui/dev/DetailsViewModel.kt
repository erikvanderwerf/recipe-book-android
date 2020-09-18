package com.gmail.eski787.recipebook.ui.dev

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gmail.eski787.recipebook.data.Item
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult
import com.gmail.eski787.recipebook.repo.RoomRepositoryAggregator
import kotlinx.coroutines.Dispatchers

class DetailsViewModel : ViewModel() {
    fun collectDetailsFor(identifier: OpenRecipeIdentifier): LiveData<ProgressResult<Item>> {
        return liveData(context = Dispatchers.IO) {
            emit(ProgressResult.Loading("Loading..."))
            emit(RoomRepositoryAggregator.merge().getItem(identifier))
        }
    }
}