package com.moon.android.mondaysally.ui.main.clover

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityCloverRankingBinding
import com.moon.android.mondaysally.ui.BaseActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CloverRankingActivity : BaseActivity<ActivityCloverRankingBinding>() {

    private val cloverViewModel: CloverViewModel by viewModel()
    private lateinit var cloverRankingAdapter: CloverRankingAdapter

    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_clover_ranking

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = cloverViewModel

        cloverViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        cloverViewModel.rankingList.observe(this, { rankingList ->
            if (rankingList.isNotEmpty()) {
                binding.activityCloverRankingTvFirstRankNickname.text = rankingList[0].nickname
                binding.activityCloverRankingTvFirstRankClover.text = DecimalFormat("#,###").format(rankingList[0].currentClover)
                setCircleImageByUrl(
                    binding.activityCloverRankingIvFirstRankProfile,
                    rankingList[0].imgUrl
                )
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun initAfterBinding() {
        cloverRankingAdapter = CloverRankingAdapter()
        binding.activityCloverRankingRv.adapter = cloverRankingAdapter

        cloverRankingAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {

                }
                is LoadState.NotLoading -> {
                    if (cloverRankingAdapter.itemCount == 0) {
                    } else {
                    }
                }
                is LoadState.Error -> {
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formatted = current.format(formatter)
            binding.activityCloverRankingTvDate.text = "$formatted 기준"
        } else {
//            TODO("VERSION.SDK_INT < O")
        }
        cloverViewModel._getRankingList()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            cloverViewModel.twinkleRankingFlow.collectLatest { pagingData ->
                cloverRankingAdapter.submitData(pagingData)
            }
        }
    }
}