package com.timber.soft.moresounds

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timber.soft.moresounds.data.DataListModel
import com.timber.soft.moresounds.databinding.ActivityDetailsBinding
import com.timber.soft.moresounds.databinding.ActivityPlayBinding
import com.timber.soft.moresounds.tools.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding

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

        val dataListModel = intent.getSerializableExtra("KEY_EXTRA") as DataListModel

        binding.playLike.isSelected = dataListModel.isCollect
        binding.playBack.setOnClickListener() {
            finish()
        }

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
                //TODO: 实现取消喜欢
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.dataBase.getDataListModelDao().deleteData(dataListModel.apply {
                        isCollect = binding.playLike.isSelected
                    })
                }
            }
        }
    }
}