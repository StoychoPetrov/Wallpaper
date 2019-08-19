package com.wallpapers.wallpaper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wallpapers.wallpaper.databinding.ItemLabelBinding

class LabelAdapter(private val listener: RecyclerViewListener) : RecyclerView.Adapter<LabelAdapter.LabelViewHolder>(){

    interface RecyclerViewListener {
        fun onItemClicked(position: Int)
    }

    private val titlesArrayList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder
            = LabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false), listener)

    override fun getItemCount(): Int = titlesArrayList.size

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) = holder.bindTo(titlesArrayList[position], position)

    class LabelViewHolder(parent: View, val listener: RecyclerViewListener): RecyclerView.ViewHolder(parent) {

        private val binding = ItemLabelBinding.bind(parent)

        fun bindTo(title: String, position: Int) {
            binding.title           = title

            binding.titleTxt.setOnClickListener {
                listener.onItemClicked(position)
            }
        }
    }

    fun updateData(items: List<String>) {
        val diffCallback    = LabelsItemDiffCallback(titlesArrayList, items)
        val diffResult      = DiffUtil.calculateDiff(diffCallback)

        titlesArrayList.clear()
        titlesArrayList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class LabelsItemDiffCallback(private val oldList: List<String>,
                                 private val newList: List<String>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
                = oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}