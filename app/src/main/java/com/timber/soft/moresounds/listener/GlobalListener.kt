package com.timber.soft.moresounds.listener

import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.data.DataListModel

interface OnHomeItemClickListener {
    fun onItemClick(position: Int, categoryModel: CategoryModel)
}


interface OnDetailsItemClickListener {
    fun onItemClick(position: Int, dataListModel: DataListModel)
}