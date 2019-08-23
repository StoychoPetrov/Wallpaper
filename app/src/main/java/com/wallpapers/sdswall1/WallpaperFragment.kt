package com.wallpapers.sdswall1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.wallpapers.sdswall1.databinding.FragmentWallpaperBinding
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import android.app.WallpaperManager
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_wallpaper.adView
import java.io.IOException


class WallpaperFragment : Fragment() {

    private lateinit var binding: FragmentWallpaperBinding

    private val args: WallpaperFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding                                         = FragmentWallpaperBinding.inflate(inflater, container, false)
        binding.lifecycleOwner                          = viewLifecycleOwner
        binding.wallpaperModel                          = args.wallpaper

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWallpaperClickListener()

        MobileAds.initialize(requireContext()) {

        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setWallpaperClickListener(){
        setWallpaperBtn.setOnClickListener {
            val myWallpaperManager = WallpaperManager.getInstance(requireContext())
            try {
                val drawableResourceId =
                    this.resources.getIdentifier(args.wallpaper.name, "drawable", requireContext().packageName)

                myWallpaperManager.setResource(drawableResourceId)

                Toast.makeText(requireContext(), "Wallpaper is set successful!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}