package com.wallpapers.wallpaper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallpaperModel(
    val name: String
) : Parcelable