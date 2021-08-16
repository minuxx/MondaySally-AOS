package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageViewpagerAdapter(
    fragmentActivity: FragmentActivity,
    private val twinkleImgList: List<String>,
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = twinkleImgList.size

    override fun createFragment(position: Int): Fragment {
        return ImageFragment(twinkleImgList[position])
    }
}