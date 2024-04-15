package com.timber.soft.moresounds.data

import java.io.Serializable

data class CategoryModel(
    val categoryId: String,
    val categoryName: String,
    val categoryUrl: String,
    val list: List<DataListModel>
) : Serializable
