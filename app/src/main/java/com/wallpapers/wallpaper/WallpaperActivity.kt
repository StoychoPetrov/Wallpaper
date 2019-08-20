package com.wallpapers.wallpaper

import android.app.WallpaperManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.wallpapers.wallpaper.databinding.ActivityWallpaperBinding
import kotlinx.android.synthetic.main.activity_wallpaper.*
import kotlinx.android.synthetic.main.fragment_wallpaper.adView
import kotlinx.android.synthetic.main.fragment_wallpaper.setWallpaperBtn
import java.io.IOException
import android.net.Uri


class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        binding                         = DataBindingUtil.setContentView(this, R.layout.activity_wallpaper)
        binding.lifecycleOwner          = this
        binding.wallpaperModel          = intent.getParcelableExtra("wallpaper")

        setWallpaperClickListener()

        MobileAds.initialize(this) {

        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        backImg.setOnClickListener {
            onBackPressed()
        }

        shareImg.setOnClickListener{
            shareImage()
        }
    }

    private fun setWallpaperClickListener(){
        setWallpaperBtn.setOnClickListener {
            val myWallpaperManager = WallpaperManager.getInstance(this)
            try {
                val drawableResourceId =
                    this.resources.getIdentifier(binding.wallpaperModel?.name, "drawable", this.packageName)

                myWallpaperManager.setResource(drawableResourceId)

                Toast.makeText(this, "Wallpaper is set successful!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun shareImage(){
        val sendIntent = Intent().apply {
            val resId = resources.getIdentifier(binding.wallpaperModel?.name, "drawable", packageName)

            val path = Uri.parse("android.resource://$packageName/" + resId)

            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, path)
            type = "image/jpeg"

            startActivity(Intent.createChooser(this, resources.getText(R.string.send_to)))
        }
    }
}