package com.timber.soft.moresounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.timber.soft.moresounds.adapter.LikeCardAdapter
import com.timber.soft.moresounds.databinding.FragmentLikeBinding
import com.timber.soft.moresounds.tools.LikeViewModel

class LikeFragment : Fragment() {
    private lateinit var binding: FragmentLikeBinding
    private lateinit var viewModel: LikeViewModel
    private lateinit var likeCardAdapter: LikeCardAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLikeBinding.inflate(layoutInflater)

        likeCardAdapter = LikeCardAdapter(requireContext())
        binding.recyclerLike.run {
            adapter = likeCardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.update()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            likeCardAdapter.updateData(it)
        })
    }
}