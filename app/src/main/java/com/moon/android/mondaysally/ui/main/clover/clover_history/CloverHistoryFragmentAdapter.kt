package com.moon.android.mondaysally.ui.main.clover.clover_history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moon.android.mondaysally.ui.main.clover.clover_history.fragment.AccumCloverFragment
import com.moon.android.mondaysally.ui.main.clover.clover_history.fragment.NowCloverFragment
import com.moon.android.mondaysally.ui.main.clover.clover_history.fragment.UsedCloverFragment

class CloverHistoryFragmentAdapter(
    fragmentActivity: FragmentActivity,
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AccumCloverFragment()
            1 -> NowCloverFragment()
            2 -> UsedCloverFragment()
            else -> UsedCloverFragment()
        }
    }
}