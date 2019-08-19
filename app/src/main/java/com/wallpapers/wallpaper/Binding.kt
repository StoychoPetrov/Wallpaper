package com.wallpapers.wallpaper

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object Binding {

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(imageView: ImageView, wallpaper: WallpaperModel) {
        val resId = imageView.resources.getIdentifier(wallpaper.name, "drawable", imageView.context.packageName)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, resId))
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: List<String>) {
        with(recyclerView.adapter as LabelAdapter) {
            updateData(resource)
        }
    }
}