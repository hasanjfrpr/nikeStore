package com.example.digikala.feature.main.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.digikala.R
import com.example.digikala.data.repo.Banner
import com.example.digikala.views.NikeLoadingImage

class BannerSliderAdapter(fragment:Fragment,val banner : List<Banner>) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int =banner.size

    override fun createFragment(position: Int): Fragment =
        BannerFragment.newInstance(banner[position])
}