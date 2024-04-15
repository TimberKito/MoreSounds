package com.timber.soft.moresounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timber.soft.moresounds.databinding.FragmentLikeBinding

class LikeFragment : Fragment() {
    private lateinit var binding: FragmentLikeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLikeBinding.inflate(layoutInflater)
        return binding.root
    }
}