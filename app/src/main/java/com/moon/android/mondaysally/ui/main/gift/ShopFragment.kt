package com.moon.android.mondaysally.ui.main.gift

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentShopBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.gift.paging.GiftShopAdapter
import com.moon.android.mondaysally.ui.main.gift.shop_detail.ShopDetailActivity
import com.moon.android.mondaysally.utils.GridItemDecoration_15_15
import com.moon.android.mondaysally.utils.GridItemDecoration_16_22
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment() :
    BaseFragment<FragmentShopBinding>() {

    private val giftViewModel: GiftViewModel by viewModel()
    private lateinit var giftShopAdapter: GiftShopAdapter

    override fun getLayoutResId() = R.layout.fragment_shop

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = giftViewModel

        giftViewModel.isLoading.observe(this, { isLoading ->

        })
    }

    override fun initAfterBinding() {
        giftShopAdapter = GiftShopAdapter()
        binding.fragmentShopRvGift.adapter = giftShopAdapter

        giftShopAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    if (giftShopAdapter.itemCount == 0) {
                        binding.fragmentShopTvNoData.visibility = VISIBLE
                    } else {
                        binding.fragmentShopTvNoData.visibility = GONE
                    }
                    giftViewModel.giftCount.value = giftShopAdapter.itemCount
                }
                is LoadState.Error -> {
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }
        giftShopAdapter.setOnItemClickListener { item ->
            giftViewModel.giftIndex.value = item.idx
            activity?.let {
                val intent = Intent(context, ShopDetailActivity::class.java)
                intent.putExtra("idx", item.idx)
                startActivity(intent)
            }
        }

        GridItemDecoration_15_15(binding.fragmentShopRvGift.context).let {
            binding.fragmentShopRvGift.addItemDecoration(
                it
            )
        }

//        context?.let { GridItemDecoration_15_15(it) }?.let {
//            binding.fragmentShopRvGift.addItemDecoration(
//                it
//            )
//        }

        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            giftViewModel.giftShopFlow.collectLatest { pagingData ->
                giftShopAdapter.submitData(pagingData)
            }
        }
    }
}