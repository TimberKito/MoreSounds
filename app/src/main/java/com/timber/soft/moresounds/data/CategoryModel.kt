package com.timber.soft.moresounds.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timber.soft.moresounds.tools.AppTools
import org.jetbrains.annotations.PropertyKey
import java.io.Serializable

@Entity(tableName = AppTools.TABLE_NAME_CATEGORY)
data class CategoryModel(
    @PrimaryKey(autoGenerate = false)
    val categoryId: String,
    val categoryName: String,
    val categoryUrl: String,
    val list: List<DataListModel>
) : Serializable
