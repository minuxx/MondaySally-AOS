package com.moon.android.mondaysally.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moon.android.mondaysally.ui.main.home.HomeFragment
import com.moon.android.mondaysally.ui.main.shop.ShopFragment
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4 //menu의 갯수
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ShopFragment()
            2 -> TwinkleFragment()
            else -> error("No Fragment")
        }
    } //createFragment

}