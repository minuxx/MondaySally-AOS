package com.moon.android.mondaysally.ui.main.gift.gift_history

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityGiftHistoryBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.gift.GiftViewModel
import com.moon.android.mondaysally.ui.main.gift.paging.GiftHistoryActivityAdapter
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.TwinkleDetailActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
import com.moon.android.mondaysally.utils.GridItemDecoration_16_22
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class GiftHistoryActivity : BaseActivity<ActivityGiftHistoryBinding>() {

    private val giftViewModel: GiftViewModel by viewModel()
    private lateinit var giftHistoryAdapter: GiftHistoryActivityAdapter

    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_gift_history

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = giftViewModel

        giftViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

    }

    override fun initAfterBinding() {
        giftHistoryAdapter = GiftHistoryActivityAdapter()
        binding.activityGiftHistoryRv.adapter = giftHistoryAdapter

        GridItemDecoration_16_22(context).let {
            binding.activityGiftHistoryRv.addItemDecoration(
                it
            )
        }

        giftHistoryAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    if (giftHistoryAdapter.itemCount == 0) {
                        binding.activityGiftTvNoData.visibility = View.VISIBLE
                    } else {
                        binding.activityGiftTvNoData.visibility = View.GONE
                    }
                    giftViewModel.giftCount.value = giftHistoryAdapter.itemCount
                }
                is LoadState.Error -> {
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }

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
        giftViewModel._getGiftHistoryCount()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            giftViewModel.giftHistoryFlow.collectLatest { pagingData ->
                giftHistoryAdapter.submitData(pagingData)
            }
        }
    }
}