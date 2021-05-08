package com.jinhyun.weggletoyproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.view.adapter.item.InterestItem
import com.jinhyun.weggletoyproject.view.adapter.viewholder.InterestItemViewHolder

class InterestGridAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var interestList: ArrayList<InterestItem> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InterestItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_interest, parent, false), itemClickListener)
    }

    override fun getItemCount(): Int {
        return interestList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val interestItem: InterestItem = interestList[position]

        val interestItemViewHolder: InterestItemViewHolder = holder as InterestItemViewHolder

        interestItemViewHolder.bind(interestItem)
    }

    fun setItems(items: ArrayList<InterestItem>) {
        this.interestList = items
    }

    fun getSelectedItems(): List<InterestItem> {
        val selectedItems: ArrayList<InterestItem> = ArrayList()

        this.interestList.forEach {
            if(it.selected) {
                selectedItems.add(it)
            }
        }

        return selectedItems
    }

    fun select(position: Int) {

        interestList[position].selected = !interestList[position].selected
        notifyItemChanged(position)
    }
}