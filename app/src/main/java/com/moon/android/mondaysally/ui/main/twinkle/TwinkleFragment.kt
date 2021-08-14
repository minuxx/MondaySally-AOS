package com.moon.android.mondaysally.ui.main.twinkle

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTwinkleBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleFragment() :
    BaseFragment<FragmentTwinkleBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()

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

        twinkleViewModel.fail.observe(this, { fail ->
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

        binding.fragmentTwinkleSwipeRefresh.setColorSchemeResources(
            R.color.pinkish_orange
        )
        binding.fragmentTwinkleSwipeRefresh.setOnRefreshListener {
            //리로딩
            twinkleViewModel._getMyTwinkleList()
            twinkleViewModel._getTwinkleList()
        }
    }

    override fun initAfterBinding() {
        twinkleViewModel.isLoading.value = true
        twinkleViewModel._getMyTwinkleList()
        twinkleViewModel._getTwinkleList()
    }

}