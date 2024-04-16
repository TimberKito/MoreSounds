package com.timber.soft.moresounds.listener

import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.data.DataListModel

fun interface OnHomeItemClickListener {
    fun onItemClick(position: Int, categoryModel: CategoryModel)
}


fun interface OnDetailsItemClickListener {
    fun onItemClick(position: Int, dataListModel: DataListModel)
}