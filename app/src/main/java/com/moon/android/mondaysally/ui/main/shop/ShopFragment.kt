package com.moon.android.mondaysally.ui.main.shop

import android.content.Intent
import android.util.Log
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Gift

import com.moon.android.mondaysally.databinding.FragmentShopBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.shop.shop_detail.ShopDetailActivity
import com.moon.android.mondaysally.ui.terms.TermsActivity
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

        })

        shopViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
            } else {
            }
        })

        val giftShopAdapter = GiftShopAdapter()
        giftShopAdapter.setOnItemClickListener { item ->
            shopViewModel.giftIndex.value = item.idx
            activity?.let{
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
            when(fail.code){
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