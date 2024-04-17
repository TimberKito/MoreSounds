package com.timber.soft.moresounds

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Database
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timber.soft.moresounds.data.DataListModel
import com.timber.soft.moresounds.databinding.ActivityPlayBinding
import com.timber.soft.moresounds.tools.AppDatabase
import com.timber.soft.moresounds.tools.DownloadMp3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.Timer
import java.util.TimerTask

class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var dataListModel: DataListModel
    private var isSeekbarChaning: Boolean = false
    private var isDownload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.TRANSPARENT
        }

        dataListModel = intent.getSerializableExtra("KEY_EXTRA") as DataListModel

        binding.playLike.isSelected = dataListModel.isCollect

        binding.playTitleName.text = dataListModel.title

        try {
            Glide.with(this).load(dataListModel.preUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                // TODO 设置加载失败占位图方法
                .into(binding.playPreImg)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.playLike.setOnClickListener() {
            if (!binding.playLike.isSelected) {
                binding.playLike.isSelected = !binding.playLike.isSelected
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.dataBase.getDataListModelDao().insertData(dataListModel.apply {
                        isCollect = binding.playLike.isSelected
                    })
                }

            } else {
                binding.playLike.isSelected = !binding.playLike.isSelected
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.dataBase.getDataListModelDao().deleteData(dataListModel.apply {
                        isCollect = binding.playLike.isSelected
                    })
                }
            }
        }

        initMediaPlayer()

        //绑定监听器，监听拖动到指定位置
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val duration2: Int = mediaPlayer.duration / 1000 //获取音乐总时长
                val position: Int = mediaPlayer.currentPosition //获取当前播放的位置
                binding.tvStart.text = calculateTime(position / 1000) //开始时间
                binding.tvEnd.text = calculateTime(duration2) //总时长
            }

            // 重写onStartTrackingTouch方法，当开始跟踪触摸时调用
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isSeekbarChaning = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                isSeekbarChaning = false
                mediaPlayer.seekTo(seekBar.progress) //在当前位置播放
                binding.tvStart.text = calculateTime(mediaPlayer.currentPosition / 1000)
            }
        })




        binding.play.setOnClickListener() {
            if (!mediaPlayer.isPlaying) {
                if (isDownload) {
                    play()
                } else {
                    binding.progressbar.isVisible = true
                    startDownload()
                }

            }
        }


        binding.playBack.setOnClickListener() {
            finish()
        }


        binding.pause.setOnClickListener() {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }


    }

    private fun startDownload() {
        dataListModel.mp3Url?.let {
            DownloadMp3.glideDownload(this, it) { file ->
                if (file == null) {
                    isDownload = false
                    Toast.makeText(
                        this@PlayActivity, "Sorry, the download failed.", Toast.LENGTH_SHORT
                    ).show()
                    binding.progressbar.isVisible = false
                } else {
                    binding.progressbar.isVisible = false
                    file.absolutePath.let { path ->
                        mediaPlayer?.setDataSource(path)
                        mediaPlayer?.prepare()
                        isDownload = true
                        play()
                        CoroutineScope(Dispatchers.IO).launch {
                            AppDatabase.dataBase.getDataListModelDao()
                                .updateData(dataListModel.apply {
                                    downloadUrl = path
                                })
                        }
                    }
                }


            }
        }
    }

    private fun play() {
        binding.seekbar.setMax(mediaPlayer.duration)
        Timer().run {
            schedule(object : TimerTask() {
                override fun run() {
                    if (!isSeekbarChaning) {
                        binding.seekbar.progress = mediaPlayer.currentPosition
                    }
                }
            }, 0, 50)
        }
        mediaPlayer.start()

    }


    private fun initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer.isLooping = true
            dataListModel.downloadUrl.let {
                mediaPlayer.setDataSource(it)
                mediaPlayer.prepare()
                isDownload = true
            }

            binding.tvStart.text = calculateTime(mediaPlayer.currentPosition / 1000)
            binding.tvEnd.text = calculateTime(mediaPlayer.duration / 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun calculateTime(time: Int): String {
        val minute: Int
        val second: Int
        return if (time >= 60) {
            minute = time / 60
            second = time % 60
            //分钟在0~9
            if (minute < 10) {
                //判断秒
                if (second < 10) {
                    "0$minute:0$second"
                } else {
                    "0$minute:$second"
                }
            } else {
                //分钟大于10再判断秒
                if (second < 10) {
                    "$minute:0$second"
                } else {
                    "$minute:$second"
                }
            }
        } else {
            second = time
            if (second in 0..9) {
                "00:0$second"
            } else {
                "00:$second"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}