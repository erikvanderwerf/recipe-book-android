package com.gmail.eski787.recipebook.ui.dev

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.repo.Result
import com.gmail.eski787.recipebook.repo.RoomRepositoryAggregator
import kotlinx.coroutines.Dispatchers

class IndexViewModel : ViewModel() {
    fun collectIndex(): LiveData<Result<List<IndexedItem>>> {
        return liveData(context = Dispatchers.IO) {
            emit(Result.Loading("Loading..."))
            emit(RoomRepositoryAggregator.merge().getIndex())
        }
    }
}