package com.wallpapers.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_about.*

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        toolbar                 = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        interstitialAd              = InterstitialAd(this)
        interstitialAd!!.adUnitId   = BuildConfig.AD_INTERSTITIAL_ID

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

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
