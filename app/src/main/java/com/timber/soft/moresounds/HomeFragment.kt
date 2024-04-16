package com.timber.soft.moresounds

import android.content.Intent
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.timber.soft.moresounds.adapter.HomeCardAdapter
import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.data.DataListModel
import com.timber.soft.moresounds.databinding.FragmentHomeBinding
import com.timber.soft.moresounds.listener.OnHomeItemClickListener
import com.timber.soft.moresounds.tools.AppTools

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val categoryModels = AppTools.parseJsonFile(requireActivity().assets.open("resource.json"))

        binding.recyclerHome.run {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = HomeCardAdapter(
                requireContext(),
                categoryModels
            ) { _, categoryModel ->
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra("KEY_EXTRA", categoryModel)
                startActivity(intent)
                Log.d("HomeOnClick", "item has been click!")
            }
        }

        return binding.root
    }

}



