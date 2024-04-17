package com.timber.soft.moresounds.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timber.soft.moresounds.PlayActivity
import com.timber.soft.moresounds.R
import com.timber.soft.moresounds.data.DataListModel

class LikeCardAdapter(
    private val context: Context,
) : RecyclerView.Adapter<LikeCardAdapter.ItemViewHolder>() {

    private var likeDataList: List<DataListModel> = emptyList()

    fun updateData(dataList: List<DataListModel>) {
        likeDataList = dataList
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgItemPer = itemView.findViewById<ImageView>(R.id.img_like)
        val textItemName = itemView.findViewById<TextView>(R.id.tv_like_name)
        val rootCard = itemView.findViewById<LinearLayout>(R.id.like_layout_rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_like_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return likeDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        likeDataList[position].run {
            holder.textItemName.text = title
            Glide.with(context).load(preUrl).transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.svg_loading_error)
                .into(holder.imgItemPer)
            holder.rootCard.setOnClickListener() {
                context.startActivity(Intent(context, PlayActivity::class.java).apply {
                    putExtra("KEY_EXTRA", this@run)
                })
            }
        }
    }
}