package com.wallpapers.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*
import android.content.pm.PackageManager
import android.R.attr.versionName
import android.content.pm.PackageInfo
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd


class AboutActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        toolbar                 = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        try {
            val pInfo   = getPackageManager().getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            versionTxt.text = version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        interstitialAd              = InterstitialAd(this)
        interstitialAd!!.adUnitId   = BuildConfig.AD_INTERSTITIAL_ID

        loadAd()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }

        return true
    }
}
