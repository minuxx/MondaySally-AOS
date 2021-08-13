package com.moon.android.mondaysally.ui.main.shop

import android.content.Intent
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentShopBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.home.GiftHistoryAdapter
import com.moon.android.mondaysally.ui.main.shop.shop_detail.ShopDetailActivity
import com.moon.android.mondaysally.utils.GridItemDecoration_15_15
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment() :
    BaseFragment<FragmentShopBinding>() {

    private val shopViewModel: ShopViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_shop

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = shopViewModel

        shopViewModel.giftTotalCount.observe(this, { giftTotalCount ->
            //GridView높이 동적으로 측정해서 조정해주기 필요함
//            val measuredHeight: Int = binding.fragmentShopRvGift.measuredHeight
//            val params: ViewGroup.LayoutParams = binding.fragmentShopRvGift.layoutParams
//            val row = giftTotalCount.div(2)
//            params.height = measuredHeight * (row)
//            binding.fragmentShopRvGift.layoutParams = params
//            binding.fragmentShopRvGift.requestLayout()
        })

        shopViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
            } else {
            }
        })

        val giftShopAdapter = GiftShopAdapter()
        giftShopAdapter.setOnItemClickListener { item ->
            shopViewModel.giftIndex.value = item.idx
            activity?.let {
                val intent = Intent(context, ShopDetailActivity::class.java)
                intent.putExtra("idx", item.idx)
                startActivity(intent)
            }
        }

        binding.fragmentShopRvGift.adapter = giftShopAdapter
        context?.let { GridItemDecoration_15_15(it) }?.let {
            binding.fragmentShopRvGift.addItemDecoration(
                it
            )
        }

        shopViewModel.fail.observe(this, { fail ->
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
        shopViewModel.getGiftList1()
    }

}