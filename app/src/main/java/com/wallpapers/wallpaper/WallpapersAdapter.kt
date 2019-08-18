package com.wallpapers.wallpaper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wallpapers.wallpaper.databinding.ItemWallpaperBinding

class WallpapersAdapter : RecyclerView.Adapter<WallpapersAdapter.WallpapersViewHolder>()  {

    private val wallpapersNames: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder
            = WallpapersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wallpaper, parent, false))

    override fun getItemCount(): Int = wallpapersNames.size

    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) = holder.bindTo(wallpapersNames[position])

    fun updateData(items: List<String>) {
        val diffCallback    = WallpapersItemDiffCallback(wallpapersNames, items)
        val diffResult      = DiffUtil.calculateDiff(diffCallback)

        wallpapersNames.clear()
        wallpapersNames.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class WallpapersItemDiffCallback(private val oldList: List<String>,
                                    private val newList: List<String>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
                = oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    class WallpapersViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

        private val binding             = ItemWallpaperBinding.bind(parent)

        fun bindTo(wallpaperName: String) {
            binding.wallpaper         = WallpaperModel(wallpaperName)
        }
    }
}