package com.timber.soft.moresounds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.transcode.DrawableBytesTranscoder
import com.timber.soft.moresounds.R

import com.timber.soft.moresounds.data.CategoryModel
import com.timber.soft.moresounds.listener.OnHomeItemClickListener

class HomeCardAdapter(
    private val context: Context,
    private val categoryModels: List<CategoryModel>,
    private val onHomeItemClickListener: OnHomeItemClickListener
) : RecyclerView.Adapter<HomeCardAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgCategoryPer = itemView.findViewById<ImageView>(R.id.img_category_per)
        val textCategoryName = itemView.findViewById<TextView>(R.id.text_category_name)
        val rootCard = itemView.findViewById<CardView>(R.id.item_HomeCard_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryModels.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val categoryModel = categoryModels[position]
        holder.textCategoryName.text = categoryModel.categoryName

        try {
            Glide.with(context).load(categoryModel.categoryUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.svg_loading_error)
                .into(holder.imgCategoryPer)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.rootCard.setOnClickListener() {
            onHomeItemClickListener.onItemClick(position, categoryModel)
        }

    }


}