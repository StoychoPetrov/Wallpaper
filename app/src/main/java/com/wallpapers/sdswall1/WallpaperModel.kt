package com.wallpapers.sdswall1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallpaperModel(
    var name: String?        = null
) : Parcelable