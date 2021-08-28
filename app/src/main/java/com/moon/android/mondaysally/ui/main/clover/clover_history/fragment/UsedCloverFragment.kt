package com.moon.android.mondaysally.ui.main.clover.clover_history.fragment

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentUsedCloverBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.clover.CloverViewModel
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.UsedCloverHistoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsedCloverFragment() :
    BaseFragment<FragmentUsedCloverBinding>() {

    private val cloverViewModel: CloverViewModel by viewModel()
    private lateinit var usedCloverHistoryAdapter: UsedCloverHistoryAdapter

    override fun getLayoutResId() = R.layout.fragment_used_clover

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = cloverViewModel
    }

    override fun initAfterBinding() {
        usedCloverHistoryAdapter = UsedCloverHistoryAdapter()
        binding.fragmentUsedCloverRv.adapter = usedCloverHistoryAdapter

        usedCloverHistoryAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    if (usedCloverHistoryAdapter.itemCount == 0) {
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
        cloverViewModel._getCloverHistory("used")
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            cloverViewModel.usedCloverHistoryFlow.collectLatest { pagingData ->
                usedCloverHistoryAdapter.submitData(pagingData)
            }
        }
    }
}