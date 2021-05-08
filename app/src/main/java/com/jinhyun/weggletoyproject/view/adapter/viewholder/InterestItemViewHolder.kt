package com.jinhyun.weggletoyproject.view.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.jinhyun.weggletoyproject.view.adapter.InterestGridAdapter
import com.jinhyun.weggletoyproject.view.adapter.item.InterestItem
import kotlinx.android.synthetic.main.item_interest.view.*

class InterestItemViewHolder(view: View, listener: InterestGridAdapter.OnItemClickListener?) :
    RecyclerView.ViewHolder(view) {
    private val name: TextView = view.tv_name_item_interest
    private val image: ImageView = view.iv_image_item_interest
    private val checkBackground: View = view.v_cover_item_interest
    private val checkIcon: ImageView = view.iv_cover_item_interest

    init {
        view.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(item: InterestItem) {

        name.text = item.name

        image.load(item.drawableId) {
            crossfade(true)
        }

        when (item.selected) {
            true -> {
                checkBackground.visibility = View.VISIBLE
                checkIcon.visibility = View.VISIBLE
            }
            false -> {
                checkBackground.visibility = View.INVISIBLE
                checkIcon.visibility = View.INVISIBLE
            }
        }
    }
}