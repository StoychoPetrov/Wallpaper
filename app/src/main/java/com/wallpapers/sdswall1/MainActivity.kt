package com.wallpapers.sdswall1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar                 = findViewById(R.id.my_toolbar)
        navController           = findNavController(R.id.nav_host_fragment)
        appBarConfiguration     = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)

        toolbar.setupWithNavController(navController    , appBarConfiguration)
        setupActionBarWithNavController(navController   , appBarConfiguration)

        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            my_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        MobileAds.initialize(this, "ca-app-pub-1205229788701742~5571394914")

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

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

    private fun onShareClicked(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=$packageName")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    private fun openPlayStore(){
        val appPackageName = packageName // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

//            R.id.category -> {
//                val directions = AllWallpapersFragmentDirections.actionAllWallpapersToCategories()
//                findNavController(R.id.nav_host_fragment).navigate(directions)
//            }

            R.id.about_us -> startActivity(Intent(this, AboutActivity::class.java))

            R.id.privacy_policy -> startActivity(Intent(this, PrivacyPolicyActivity::class.java))

            R.id.share -> onShareClicked()

            R.id.rate_us -> openPlayStore()
        }

        loadAd()

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}
