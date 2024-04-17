package com.timber.soft.moresounds.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timber.soft.moresounds.tools.AppTools
import java.io.Serializable

@Entity(tableName = AppTools.TABLE_NAME_SOUNDS)
data class DataListModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val classId: String? = null,
    var isCollect: Boolean = false,
    var downloadUrl: String? = null,

    val mp3Url: String,
    val preUrl: String,
    val title: String
) : Serializable
