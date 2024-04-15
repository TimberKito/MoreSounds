package com.timber.soft.moresounds

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.timber.soft.moresounds.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnPageChangeListener {

    private lateinit var binding: ActivityMainBinding
    private var viewPagerFragment = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.TRANSPARENT
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPagerFragment.add(HomeFragment())
        viewPagerFragment.add(LikeFragment())
        viewPagerFragment.add(InfoFragment())

        binding.viewpager.run {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getCount(): Int {
                    return viewPagerFragment.size
                }

                override fun getItem(position: Int): Fragment {
                    return viewPagerFragment[position]
                }
            }
        }
        setTabSelect(0)
        binding.tabHome.setOnClickListener {
            setTabSelect(0)
        }
        binding.tabLike.setOnClickListener {
            setTabSelect(1)
        }
        binding.tabInfo.setOnClickListener {
            setTabSelect(2)
        }
    }

    private fun setTabSelect(position: Int) {
        when (position) {
            0 -> {
                binding.tabHomeImg.isSelected = true
                binding.tabLikeImg.isSelected = false
                binding.tabInfoImg.isSelected = false
                binding.viewpager.currentItem = 0
            }

            1 -> {
                binding.tabHomeImg.isSelected = false
                binding.tabLikeImg.isSelected = true
                binding.tabInfoImg.isSelected = false
                binding.viewpager.currentItem = 1
            }

            2 -> {
                binding.tabHomeImg.isSelected = false
                binding.tabLikeImg.isSelected = false
                binding.tabInfoImg.isSelected = true
                binding.viewpager.currentItem = 2
            }

        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        setTabSelect(position)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
