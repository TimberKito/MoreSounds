package com.timber.soft.moresounds.data

import java.io.Serializable

data class DataListModel(
    val mp3Url: String, val preUrl: String, val title: String
) : Serializable
