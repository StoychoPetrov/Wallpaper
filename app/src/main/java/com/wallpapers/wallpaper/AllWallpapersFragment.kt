package com.wallpapers.wallpaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wallpapers.wallpaper.databinding.FragmentAllWallpapersBinding


class AllWallpapersFragment : Fragment() {

    private lateinit var binding: FragmentAllWallpapersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding                                         = FragmentAllWallpapersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner                          = viewLifecycleOwner
        binding.wallpapersRecyclerView.adapter          = WallpapersAdapter()
        binding.wallpapersRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, 0, false))

        val names   = resources.getStringArray(R.array.all_wallpapers)

        (binding.wallpapersRecyclerView.adapter as WallpapersAdapter).updateData(names.toList())

        return binding.root
    }
}
