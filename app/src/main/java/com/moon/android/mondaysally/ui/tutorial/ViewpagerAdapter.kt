package com.moon.android.mondaysally.ui.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.ui.tutorial.fragment.TutorialFragment
import java.util.*


class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val titleList: List<String>,
    val contentList: List<String>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return TutorialFragment(R.drawable.illust_point, titleList[0], contentList[0])
        } else if (position == 1) {
            return TutorialFragment(R.drawable.illust_point, titleList[1], contentList[1])
        } else {
            return TutorialFragment(R.drawable.illust_twinkle, titleList[2], contentList[2])
        }
    }
}