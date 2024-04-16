package com.timber.soft.moresounds.tools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timber.soft.moresounds.data.DataListModel
import kotlinx.coroutines.launch

class LikeViewModel : ViewModel() {

    private var likeData: MutableLiveData<List<DataListModel>> =
        MutableLiveData<List<DataListModel>>()

    init {
        viewModelScope.launch {
            likeData.value = AppDatabase.dataBase.getDataListModelDao().getCollectData()
        }
    }

    fun update() {
        viewModelScope.launch {
            likeData.value = AppDatabase.dataBase.getDataListModelDao().getCollectData()
        }
    }

    fun getList() = likeData

    override fun onCleared() {
        super.onCleared()
    }

}