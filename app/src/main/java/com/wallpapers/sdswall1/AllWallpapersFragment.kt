package com.wallpapers.sdswall1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wallpapers.sdswall1.databinding.FragmentAllWallpapersBinding


class AllWallpapersFragment : Fragment(), WallpapersAdapter.RecyclerViewListener {

    private lateinit var binding: FragmentAllWallpapersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding                                         = FragmentAllWallpapersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner                          = viewLifecycleOwner
        binding.wallpapersRecyclerView.adapter          = WallpapersAdapter(this)
        binding.wallpapersRecyclerView.setHasFixedSize(true)
        binding.wallpapersRecyclerView.setItemViewCacheSize(20)
        binding.wallpapersRecyclerView.setDrawingCacheEnabled(true)
        binding.wallpapersRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        binding.wallpapersRecyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10, false))

        val names   = resources.getStringArray(R.array.all_wallpapers)

        (binding.wallpapersRecyclerView.adapter as WallpapersAdapter).updateData(names.toList())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemClicked(wallpaper: WallpaperModel) {

        val intent = Intent(requireActivity(), WallpaperActivity::class.java)
        intent.putExtra("wallpaper", wallpaper)
        startActivity(intent)
    }
}
