package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import android.content.Context
import android.view.View.GONE
import androidx.viewpager2.widget.ViewPager2
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTwinkleDetailBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleViewModel
import me.relex.circleindicator.CircleIndicator3
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleDetailActivity : BaseActivity<ActivityTwinkleDetailBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()
    lateinit var context: Context

    lateinit var imageViewPager: ViewPager2
    lateinit var indicator: CircleIndicator3
    lateinit var viewPagerAdapter: ImageViewpagerAdapter

    override fun getLayoutResId() = R.layout.activity_twinkle_detail

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel

        imageViewPager = binding.activityTwinkleDetailVp2


//        twinkleViewModel.showDialog.observe(this, { showDialog ->
//            if (showDialog) {
//                showSallyDialog(
//                    this,
//                    getString(R.string.shop_apply_check),
//                    getString(R.string.ok),
//                    object : SallyDialog.DialogClickListener {
//                        override fun onOKClicked() {
//                            shopViewModel.postGift()
//                        }
//                    })
//            }
//        })

        twinkleViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        twinkleViewModel.twinkleResult.observe(this, { twinkleResult ->
            viewPagerAdapter = ImageViewpagerAdapter(this, twinkleResult.twinkleImglists)
            imageViewPager.adapter = viewPagerAdapter

            indicator = binding.activityTwinkleDetailIndicator
            indicator.setViewPager(imageViewPager)
            indicator.createIndicators(twinkleResult.twinkleImglists.size, 0)
            if (twinkleResult.twinkleImglists.size == 1)
                binding.activityTwinkleDetailIndicator.visibility = GONE
        })

        twinkleViewModel.fail.observe(this, { fail ->
//            341	"존재하지 않는 사용자입니다."
//            346	"해당 사원은 탈퇴회원입니다."
//            353	"신청에 필요한 클로버가 현재 클로버보다 많습니다."
//            370	"존재하지 않는 회사입니다."
//            377	"탈퇴한 회사입니다."
//            388	"JWT토큰을 입력해주세요."
//            389	"유효하지 않은 JWT토큰입니다."
//            404	"네트워크 오류가 발생했습니다."
            when (fail.code) {
                353, 346, 370, 377 -> {
                    showToast(fail.message)
                }
                341, 388, 389, 404 -> {
                    showToast(getString(R.string.default_fail))
                }
            }
        })
    }

    override fun initAfterBinding() {
        twinkleViewModel.getTwinkleDetail(intent.getIntExtra("idx", 0))
    }
}