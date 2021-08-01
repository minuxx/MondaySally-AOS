package com.moon.android.mondaysally.ui.main.home

import android.util.Log
import android.view.View
import android.view.View.*
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.utils.GridItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() :
    BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val adapter = GiftHistoryAdapter()
    override fun getLayoutResId() = R.layout.fragment_home

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = homeViewModel

        homeViewModel.homeResult.observe(this, { homeResult ->
            homeResult.twinkleRank.forEachIndexed { index, twinkleRank ->
                if (index == 0) {
                    setCircleImageByGlide(binding.fragmentHomeIvTwinkleFirst, twinkleRank.imgUrl)
                    binding.fragmentHomeTvTwinkleFirst.text = twinkleRank.nickname
                } else if (index == 1) {
                    setCircleImageByGlide(binding.fragmentHomeIvTwinkleSecond, twinkleRank.imgUrl)
                    binding.fragmentHomeTvTwinkleSecond.text = twinkleRank.nickname
                } else {
                    setCircleImageByGlide(binding.fragmentHomeIvTwinkleThird, twinkleRank.imgUrl)
                    binding.fragmentHomeTvTwinkleThird.text = twinkleRank.nickname
                }
            }
        })

        homeViewModel.giftHistoryList.observe(this, { giftHistoryList ->
            if (giftHistoryList.isEmpty()) {
                binding.fragmentHomeLinerNoHistory.visibility = VISIBLE
                binding.fragmentHomeRvGiftHistory.visibility = INVISIBLE
            }
            else {
                binding.fragmentHomeLinerNoHistory.visibility = INVISIBLE
                binding.fragmentHomeRvGiftHistory.visibility = VISIBLE
            }
        })

        homeViewModel.memberList.observe(this, { memberList ->
            if (memberList.isEmpty()) binding.fragmentHomeLinerNoMember.visibility = VISIBLE
            else binding.fragmentHomeLinerNoMember.visibility = GONE
        })

        binding.fragmentHomeRvGiftHistory.adapter = adapter
        binding.fragmentHomeRvNowOn.adapter = MemberListAdapter()
        context?.let { GridItemDecoration(it) }?.let {
            binding.fragmentHomeRvNowOn.addItemDecoration(
                it
            )
        }
    }

    override fun initAfterBinding() {
        binding.fragmentHomeSwipeRefresh.setOnRefreshListener {
            //리로딩

            //완료되면 아래코드
            binding.fragmentHomeSwipeRefresh.isRefreshing = false;
        }
        homeViewModel.getHomeData()
    }

    fun noHistoryCheck(): Int {
        homeViewModel.giftHistoryList.value?.let {
            return if (it.isEmpty()) View.VISIBLE
            else View.GONE
        }
        adapter.setOnItemClickListener { item ->
            Log.d("로그", item.imgUrl)
        }

        //null 이면 visible
        return View.VISIBLE
    }
}