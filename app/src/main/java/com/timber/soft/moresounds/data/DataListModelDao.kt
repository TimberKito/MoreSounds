package com.timber.soft.moresounds.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataListModelDao {

    @Query("select * from sounds where isCollect = :collect ")
    suspend fun getCollectData(collect: Boolean = true): List<DataListModel>

    @Update
    suspend fun updateData(dataListModel: DataListModel)

}
