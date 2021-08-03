package com.moon.android.mondaysally.ui.main.home

import android.view.View.*
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Gift
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.utils.GridItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() :
    BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()
    override fun getLayoutResId() = R.layout.fragment_home

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = homeViewModel

        homeViewModel.homeResultResult.observe(this, { homeResult ->
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
            } else {
                binding.fragmentHomeLinerNoHistory.visibility = INVISIBLE
                binding.fragmentHomeRvGiftHistory.visibility = VISIBLE
            }
        })

        homeViewModel.memberList.observe(this, { memberList ->
            if (memberList.isEmpty()) {
                binding.fragmentHomeLinerNoMember.visibility = VISIBLE
                binding.fragmentHomeRvNowOn.visibility = INVISIBLE
            } else {
                binding.fragmentHomeLinerNoMember.visibility = INVISIBLE
                binding.fragmentHomeRvNowOn.visibility = VISIBLE
            }
        })

        homeViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading)
//                binding.fragmentHomeSwipeRefresh.isRefreshing = false;
            else
                binding.fragmentHomeSwipeRefresh.isRefreshing = false;
        })

        binding.fragmentHomeRvGiftHistory.adapter = GiftHistoryAdapter()
        binding.fragmentHomeRvNowOn.adapter = MemberListAdapter()
        context?.let { GridItemDecoration(it) }?.let {
            binding.fragmentHomeRvNowOn.addItemDecoration(
                it
            )
        }

        binding.fragmentHomeSwipeRefresh.setColorSchemeResources(
            R.color.pinkish_orange
        )
    }

    override fun initAfterBinding() {
        binding.fragmentHomeSwipeRefresh.setOnRefreshListener {
            //리로딩
            homeViewModel.getHomeData()
            //완료되면 아래코드

        }
        homeViewModel.getHomeData()
    }

}