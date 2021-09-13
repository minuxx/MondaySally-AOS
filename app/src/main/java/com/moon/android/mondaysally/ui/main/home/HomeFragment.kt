package com.moon.android.mondaysally.ui.main.home

import android.content.Intent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.clover.CloverRankingActivity
import com.moon.android.mondaysally.ui.main.clover.clover_history.CloverHistoryActivity
import com.moon.android.mondaysally.ui.main.gift.gift_history.GiftHistoryActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.ImageViewpagerAdapter
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.TwinkleDetailActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
import com.moon.android.mondaysally.utils.GridItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() :
    BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var giftHistoryAdapter :GiftHistoryAdapter

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = homeViewModel

        homeViewModel.homeResultResult.observe(this, { homeResult ->
            homeResult.twinkleRank.forEachIndexed { index, twinkleRank ->
                when (index) {
                    0 -> {
                        setCircleImageByGlide(
                            binding.fragmentHomeIvTwinkleFirst,
                            twinkleRank.imgUrl
                        )
                        binding.fragmentHomeTvTwinkleFirst.text = twinkleRank.nickname
                    }
                    1 -> {
                        setCircleImageByGlide(
                            binding.fragmentHomeIvTwinkleSecond,
                            twinkleRank.imgUrl
                        )
                        binding.fragmentHomeTvTwinkleSecond.text = twinkleRank.nickname
                    }
                    else -> {
                        setCircleImageByGlide(
                            binding.fragmentHomeIvTwinkleThird,
                            twinkleRank.imgUrl
                        )
                        binding.fragmentHomeTvTwinkleThird.text = twinkleRank.nickname
                    }
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

        homeViewModel.goGiftHistory.observe(this, { goGiftHistory ->
            if (goGiftHistory) {
                startActivity(Intent(context, GiftHistoryActivity::class.java))
                homeViewModel.goGiftHistory.value = false
            }
        })

        homeViewModel.goTwinkleRanking.observe(this, { goGiftHistory ->
            if (goGiftHistory) {
                startActivity(Intent(context, CloverRankingActivity::class.java))
                homeViewModel.goTwinkleRanking.value = false
            }
        })

        homeViewModel.goCloverHistory.observe(this, { goCloverHistory ->
            if (goCloverHistory) {
                startActivity(Intent(context, CloverHistoryActivity::class.java))
                homeViewModel.goCloverHistory.value = false
            }
        })


        homeViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading)
//                binding.fragmentHomeSwipeRefresh.isRefreshing = false;
            else
                binding.fragmentHomeSwipeRefresh.isRefreshing = false;
        })

        homeViewModel.fail.observe(this, { fail ->
            when (fail.code) {
                341, 388, 389 -> {
                    showToast(fail.message)
                }
                402 -> {
                    showToast(getString(R.string.default_fail))
                }
                404 -> {
                    showToast(getString(R.string.default_fail))
                }
            }
        })
    }

    override fun initAfterBinding() {
        giftHistoryAdapter = GiftHistoryAdapter()
        giftHistoryAdapter.setOnItemClickListener { giftHistory ->
            if (giftHistory.isProved == "Y") {
                val intent = Intent(context, TwinkleDetailActivity::class.java)
                intent.putExtra("idx", giftHistory.twinkleIdx)
                startActivity(intent)
            } else {
                val intent = Intent(context, TwinklePostActivity::class.java)
                intent.putExtra("idx", giftHistory.giftLogIdx)
                intent.putExtra("name", giftHistory.name)
                intent.putExtra("usedClover", giftHistory.usedClover)
                startActivity(intent)
            }
        }

        binding.fragmentHomeRvGiftHistory.adapter = giftHistoryAdapter
        binding.fragmentHomeRvGiftHistory.adapter
        binding.fragmentHomeRvNowOn.adapter = MemberListAdapter()
        context?.let { GridItemDecoration(it) }?.let {
            binding.fragmentHomeRvNowOn.addItemDecoration(
                it
            )
        }

        binding.fragmentHomeSwipeRefresh.setColorSchemeResources(
            R.color.pinkish_orange
        )

        binding.fragmentHomeSwipeRefresh.setOnRefreshListener {
            homeViewModel.getHomeData()
        }

        homeViewModel.getHomeData()
    }

}