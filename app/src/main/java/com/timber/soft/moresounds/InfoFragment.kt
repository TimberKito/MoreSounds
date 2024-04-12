package com.timber.soft.moresounds

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timber.soft.moresounds.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater)

        binding.layoutRating.setOnClickListener {
            val url = getString(R.string.shop_link) + (activity?.packageName ?: "")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
        }

        binding.layoutShare.setOnClickListener {
            val url = getString(R.string.shop_link) + (activity?.packageName ?: "")
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(intent)
        }
        val pInfo: PackageInfo
        try {
            pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireActivity().packageManager.getPackageInfo(
                    requireActivity().packageName, PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0)
            }
            binding.textAppVersion.text = "Version: " + pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Activity is null", e.toString())
            binding.textAppVersion.text = "Version: " + ""
        }
        return binding.root
    }

}