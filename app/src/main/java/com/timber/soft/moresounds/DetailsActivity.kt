package com.timber.soft.moresounds

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.timber.soft.moresounds.adapter.DetailsCardAdapter
import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.TRANSPARENT
        }

        val categoryModel = intent.getSerializableExtra("KEY_EXTRA") as CategoryModel

        binding.detailsKindsName.text = categoryModel.categoryName

        binding.detailsBack.setOnClickListener() {
            finish()
        }

        val dataListModels = categoryModel.list

        binding.recyclerKindsItem.run {
            layoutManager = GridLayoutManager(context, 3)

            adapter =
                DetailsCardAdapter(
                    context, dataListModels
                ) { _, dataListModel ->
                    val intent = Intent(context, PlayActivity::class.java)
                    intent.putExtra("KEY_EXTRA", dataListModel)
                    startActivity(intent)
                    Log.d("DetailsOnClick", "item has been click!")
                }

        }

    }

}