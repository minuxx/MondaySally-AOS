package com.moon.android.mondaysally.ui.main.home

import android.util.Log
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.utils.GridItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() :
    BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = homeViewModel
        binding.fragmentHomeSwipeRefresh.setOnRefreshListener {
            //리로딩

            //완료되면 아래코드
            binding.fragmentHomeSwipeRefresh.isRefreshing = false;
        }

        val adapter = GiftHistoryAdapter()
        adapter.setOnItemClickListener { item->
            Log.d("로그",item.imgUrl)
        }
        binding.fragmentHomeRvGiftHistory.adapter = adapter

        binding.fragmentHomeRvNowOn.adapter = MemberListAdapter()
        context?.let { GridItemDecoration(it) }?.let {
            binding.fragmentHomeRvNowOn.addItemDecoration(
                it
            )
        }
    }

    override fun initAfterBinding() {
        homeViewModel.getHomeData()
    }

}