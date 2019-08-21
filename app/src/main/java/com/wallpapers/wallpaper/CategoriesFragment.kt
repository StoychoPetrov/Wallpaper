package com.wallpapers.wallpaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.wallpapers.wallpaper.databinding.FragmentCategoriesBinding
import kotlinx.android.synthetic.main.fragment_all_wallpapers.*

class CategoriesFragment : Fragment(), LabelAdapter.RecyclerViewListener {

    private lateinit var binding: FragmentCategoriesBinding

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel                                       = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        viewModel.categories                            = resources.getStringArray(R.array.categories).toList()

        binding                                         = FragmentCategoriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner                          = viewLifecycleOwner
        binding.recyclerView.adapter                    = LabelAdapter(this)
        binding.viewmodel                               = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        MobileAds.initialize(requireContext()) {
//
//        }

//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)
    }
    override fun onItemClicked(position: Int) {

    }
}
