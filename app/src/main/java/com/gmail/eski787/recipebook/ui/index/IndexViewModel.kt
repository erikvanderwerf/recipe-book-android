package com.gmail.eski787.recipebook.ui.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.eski787.recipebook.data.IndexedItem
import com.gmail.eski787.recipebook.repo.dev.DevRepository
import com.gmail.eski787.recipebook.repo.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class IndexViewModel: ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val mutableIndex: MutableLiveData<List<IndexedItem>> by lazy {
        MutableLiveData<List<IndexedItem>>().also {
            collectIndex()
        }
    }

    private var failure = MutableLiveData<Exception>()

    private fun collectIndex() {
        launch {
            when(val index = DevRepository.EMULATOR_HOST.fetchIndex()) {
                is Result.Success -> mutableIndex.postValue(index.data)
                is Result.Error -> failure.postValue(index.exception)
            }
        }
    }

    fun getIndexedItems(): LiveData<List<IndexedItem>> = mutableIndex
    fun getFailureStatus(): LiveData<Exception> = failure
}