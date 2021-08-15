package com.moon.android.mondaysally.ui.main.twinkle

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTwinkleBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinkleAdapter
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinkleAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleFragment() :
    BaseFragment<FragmentTwinkleBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()
    private lateinit var twinkleAdapter: TwinkleAdapter
    private lateinit var myTwinkleAdapter: MyTwinkleAdapter

    override fun getLayoutResId() = R.layout.fragment_twinkle

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel

        twinkleViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
//                (activity as MainActivity).showLottieDialog(activity as MainActivity)
            } else {
//                (activity as MainActivity).hideLottieDialog()
                binding.fragmentTwinkleSwipeRefresh.isRefreshing = false;
            }
        })
        binding.fragmentTwinkleSwipeRefresh.setColorSchemeResources(
            R.color.pinkish_orange
        )
        binding.fragmentTwinkleSwipeRefresh.setOnRefreshListener {
            twinkleAdapter.refresh()
            myTwinkleAdapter.refresh()
        }
    }

    override fun initAfterBinding() {
        twinkleAdapter = TwinkleAdapter()
        myTwinkleAdapter = MyTwinkleAdapter()

        binding.fragmentTwinkleRvTimeline.adapter = twinkleAdapter
        binding.fragmentTwinkleRvMyTwinkle.adapter = myTwinkleAdapter

        myTwinkleAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    twinkleViewModel.isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    twinkleViewModel.isLoading.value = false
                    if (myTwinkleAdapter.itemCount == 0) {
                        binding.fragmentShopTvAllGift.visibility = GONE
                        binding.fragmentTwinkleLine.visibility = GONE
                    }else{
                        binding.fragmentShopTvAllGift.visibility = VISIBLE
                        binding.fragmentTwinkleLine.visibility = VISIBLE
                    }
                }
                is LoadState.Error -> {
                    twinkleViewModel.isLoading.value = false
                    binding.fragmentShopTvAllGift.visibility = GONE
                    binding.fragmentTwinkleLine.visibility = GONE
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }

        twinkleAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    twinkleViewModel.isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    Log.d("페이징", "" + twinkleAdapter.itemCount)
                    twinkleViewModel.isLoading.value = false
                }
                is LoadState.Error -> {
                    twinkleViewModel.isLoading.value = false
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }
        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            twinkleViewModel.twinkleFlow.collectLatest { pagingData ->
                twinkleAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            twinkleViewModel.myTwinkleFlow.collectLatest { pagingData ->
                myTwinkleAdapter.submitData(pagingData)
            }
        }
    }
}