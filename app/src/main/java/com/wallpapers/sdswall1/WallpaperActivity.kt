package com.wallpapers.sdswall1

import android.app.WallpaperManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.wallpapers.sdswall1.databinding.ActivityWallpaperBinding
import kotlinx.android.synthetic.main.activity_wallpaper.*
import kotlinx.android.synthetic.main.fragment_wallpaper.adView
import kotlinx.android.synthetic.main.fragment_wallpaper.setWallpaperBtn
import java.io.IOException
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd


class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding

    private var interstitialAd: InterstitialAd? = null

    private lateinit var imagesNames: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        binding                         = DataBindingUtil.setContentView(this, R.layout.activity_wallpaper)
        binding.lifecycleOwner          = this
        binding.wallpaperModel          = intent.getParcelableExtra("wallpaper")

        setWallpaperClickListener()

        imagesNames = resources.getStringArray(R.array.all_wallpapers)

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

        interstitialAd              = InterstitialAd(this)
        interstitialAd!!.adUnitId   = BuildConfig.AD_INTERSTITIAL_ID

        loadAd()

        val adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        viewPager.adapter   = adapter

        viewPager.currentItem = intent.getIntExtra("position", 0)
    }

    private fun loadAd(){
        interstitialAd!!.loadAd(AdRequest.Builder().build())

        interstitialAd!!.adListener = object : AdListener(){
            override fun onAdLoaded() {
                super.onAdLoaded()

                interstitialAd!!.show()
            }
        }
    }

    private fun setWallpaperClickListener(){
        setWallpaperBtn.setOnClickListener {
            loadAd()
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

            loadAd()
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int = imagesNames.size

        override fun getItem(position: Int): Fragment {

            val fragment = WallpaperPagerFragment()
            val bundle   = Bundle()

            bundle.putString("image_name", imagesNames[position])
            fragment.arguments  = bundle

            return fragment
        }
    }
}
