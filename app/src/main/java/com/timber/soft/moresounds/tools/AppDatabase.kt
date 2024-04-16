package com.timber.soft.moresounds.tools

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.data.DataListModel
import com.timber.soft.moresounds.data.DataListModelDao


@Database(
    entities = [CategoryModel::class, DataListModel::class],
    version = AppTools.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val dataBase: AppDatabase by lazy {
            getInstance()
        }

        private fun getInstance(): AppDatabase {
            return Room.databaseBuilder(
                MyApplication.appContext, AppDatabase::class.java, AppTools.DB_NAME
            ).build()
        }
    }

    abstract fun getDataListModelDao(): DataListModelDao
}


class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}


