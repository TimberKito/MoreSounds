package com.timber.soft.moresounds.tools

import com.google.gson.Gson
import com.timber.soft.moresounds.data.CategoryModel
import java.io.InputStream
import java.io.InputStreamReader

object AppTools {
    fun parseJsonFile(jsonInputStream: InputStream): List<CategoryModel> {
        val reader = InputStreamReader(jsonInputStream)
        val jsonString = reader.readText()
        return Gson().fromJson(jsonString, Array<CategoryModel>::class.java).toList()
    }
}


