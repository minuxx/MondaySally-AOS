package com.moon.android.mondaysally.ui.main.clover.clover_history.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentAccumCloverBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.clover.CloverViewModel
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.AccumCloverHistoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccumCloverFragment() :
    BaseFragment<FragmentAccumCloverBinding>() {

    private val cloverViewModel: CloverViewModel by viewModel()
    private lateinit var accumCloverHistoryAdapter: AccumCloverHistoryAdapter

    override fun getLayoutResId() = R.layout.fragment_accum_clover

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = cloverViewModel

//        cloverViewModel.cloverHistoryResult.observe(this, { cloverHistoryResult ->
//            binding.fragmentAccumCloverTvClover.text =
//                cloverHistoryResult.accumulatedClover.toString()
//        })

    }

    override fun initAfterBinding() {
        accumCloverHistoryAdapter = AccumCloverHistoryAdapter()
        binding.fragmentAccumCloverRv.adapter = accumCloverHistoryAdapter

        accumCloverHistoryAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    if (accumCloverHistoryAdapter.itemCount == 0) {
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
        cloverViewModel._getCloverHistory("accumulate")
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            cloverViewModel.accumCloverHistoryFlow.collectLatest { pagingData ->
                accumCloverHistoryAdapter.submitData(pagingData)
            }
        }
    }
}