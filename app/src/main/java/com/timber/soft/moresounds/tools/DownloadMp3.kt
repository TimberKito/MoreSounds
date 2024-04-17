package com.timber.soft.moresounds.tools

import android.content.Context
import android.os.AsyncTask
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object DownloadMp3 {

    fun glideDownload(context: Context, url: String, downloadCall: (File?) -> Unit) {
        Glide.with(context).downloadOnly().load(url).addListener(object : RequestListener<File> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<File>,
                isFirstResource: Boolean
            ): Boolean {
                downloadCall.invoke(null)
                return false
            }

            override fun onResourceReady(
                resource: File,
                model: Any,
                target: Target<File>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                downloadCall.invoke(resource)

                return false
            }

        }).preload()
    }

}