package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.View.GONE
import android.view.animation.*
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
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

        twinkleViewModel.hideKeyboard.observe(this, { hideKeyboard ->
            if (hideKeyboard)
                hideKeyboard(binding.activityShopDetailEtComment)
        })

        twinkleViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        twinkleViewModel.commentPostSuccess.observe(this, { commentPostSuccess ->
            if (commentPostSuccess) {
                //ReLoading ?
                twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
                twinkleViewModel.editTextCommentString.set("")
                binding.activityShopDetailEtComment.clearFocus()
//                binding.activityTwinkleScrollView.fullScroll(View.FOCUS_DOWN)
                twinkleViewModel.commentPostSuccess.value = false
            }
        })

        twinkleViewModel.twinkleResult.observe(this, { twinkleResult ->
            viewPagerAdapter = ImageViewpagerAdapter(this, twinkleResult.twinkleImglists)
            imageViewPager.adapter = viewPagerAdapter
            indicator = binding.activityTwinkleDetailIndicator
            indicator.setViewPager(imageViewPager)
            indicator.createIndicators(twinkleResult.twinkleImglists.size, 0)
            if (twinkleResult.twinkleImglists.size == 1)
                binding.activityTwinkleDetailIndicator.visibility = GONE

            if (twinkleViewModel.commentRefresh.value == true) {
//                binding.activityTwinkleScrollView.fullScroll(ScrollView.FOCUS_DOWN)
//                binding.activityTwinkleScrollView.smoothScrollTo(0,activityTwinkleScrollView.bo)
                binding.activityTwinkleScrollView.smoothScrollToView(binding.activityShopDetailRvComment)
            }
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
        twinkleViewModel.twinkleIndex.value = intent.getIntExtra("idx", 0)
        twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
    }

    /*
    ScrollView의 상단 절대 y 좌표를 구한다.
    view의 상단 절대 y좌표를 구한다.
    2번의 절대 y좌표는 스크린 기준이기 때문에, 이미 스크롤된 영역은 계산하지 못한다. 따라서 별도로 이미 스크롤된 영역인 scrollY를 더해준다.
    그리고 그 둘을 빼면 실제로 ScrollView의 최상단과 view의 최상단 사이의 논리적 거리가 구해진다.
     */
    fun NestedScrollView.smoothScrollToView(
        view: View,
    ) {
        // 스크롤의 의미가 없다.
        if (this.getChildAt(0).height <= this.height) return

//        val y = computeDistanceToView(view)

        // (스크롤 해야하는 거리 - 현재 스크롤 된 거리) / (스크롤 몸체의 높이 - 스크롤 뷰의 높이) - 보류
//        val ratio = kotlin.math.abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()

        ObjectAnimator.ofInt(this, "scrollY", view.bottom).apply {
            duration = 1000L
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
//                TODO() 끝났을때의 동작
            }
            start()
        }
    }

    internal fun NestedScrollView.computeDistanceToView(view: View): Int {
        return kotlin.math.abs(
            calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(
                view
            ).top)
        )
    }

    private fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }
}