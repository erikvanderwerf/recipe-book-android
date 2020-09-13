package com.gmail.eski787.recipebook.ui.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gmail.eski787.recipebook.data.Metadata
import com.gmail.eski787.recipebook.data.OpenRecipeIdentifier
import com.gmail.eski787.recipebook.repo.Result
import com.gmail.eski787.recipebook.repo.RoomRepositoryAggregator
import kotlinx.coroutines.Dispatchers

class MetadataViewModel : ViewModel() {
    fun collectMetadataFor(identifier: OpenRecipeIdentifier): LiveData<Result<Metadata>> {
        return liveData(context = Dispatchers.IO) {
            emit(Result.Loading("Loading..."))
            emit(RoomRepositoryAggregator.merge().getMetadataFor(identifier))
        }
    }
}