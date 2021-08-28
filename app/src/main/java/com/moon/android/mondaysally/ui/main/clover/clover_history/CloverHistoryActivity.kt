package com.moon.android.mondaysally.ui.main.clover.clover_history

import android.content.Context
import com.google.android.material.tabs.TabLayoutMediator
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityCloverHistoryBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.clover.CloverViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CloverHistoryActivity : BaseActivity<ActivityCloverHistoryBinding>() {

    private val cloverViewModel: CloverViewModel by viewModel()
    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_clover_history

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = cloverViewModel

        cloverViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })
    }

    override fun initAfterBinding() {
        binding.activityCloverHistoryVp.adapter = CloverHistoryFragmentAdapter(this)
        TabLayoutMediator(
            binding.activityCloverHistoryTab,
            binding.activityCloverHistoryVp
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "누적클로버"
                1 -> "현재클로버"
                2 -> "사용클로버"
                else -> ""
            }
        }.attach()

    }

}