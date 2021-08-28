package com.moon.android.mondaysally.ui.main.clover.clover_history.fragment

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R

import com.moon.android.mondaysally.databinding.FragmentNowCloverBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.clover.CloverViewModel
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.NowCloverHistoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowCloverFragment() :
    BaseFragment<FragmentNowCloverBinding>() {

    private val cloverViewModel: CloverViewModel by viewModel()
    private lateinit var nowCloverHistoryAdapter: NowCloverHistoryAdapter

    override fun getLayoutResId() = R.layout.fragment_now_clover

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = cloverViewModel
    }

    override fun initAfterBinding() {
        nowCloverHistoryAdapter = NowCloverHistoryAdapter()
        binding.fragmentNowCloverRv.adapter = nowCloverHistoryAdapter

        nowCloverHistoryAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    if (nowCloverHistoryAdapter.itemCount == 0) {
//                        binding.activityGiftTvNoData.visibility = View.VISIBLE
                    } else {
//                        binding.activityGiftTvNoData.visibility = View.GONE
                    }
                }
                is LoadState.Error -> {
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }
        cloverViewModel.nickname.value = cloverViewModel.sharedPrefRepository.nickname
        cloverViewModel._getCloverHistory("current")
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            cloverViewModel.nowCloverHistoryFlow.collectLatest { pagingData ->
                nowCloverHistoryAdapter.submitData(pagingData)
            }
        }
    }
}