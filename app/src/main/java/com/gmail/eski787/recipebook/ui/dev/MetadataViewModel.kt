package com.gmail.eski787.recipebook.ui.dev

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.ProgressResult
import com.gmail.eski787.recipebook.repo.RoomRepositoryAggregator
import kotlinx.coroutines.Dispatchers

class MetadataViewModel : ViewModel() {
    fun collectMetadataFor(identifier: OpenRecipeIdentifier): LiveData<ProgressResult<Metadata>> {
        return liveData(context = Dispatchers.IO) {
            emit(ProgressResult.Loading("Loading..."))
            emit(RoomRepositoryAggregator.merge().getMetadataFor(identifier))
        }
    }
}