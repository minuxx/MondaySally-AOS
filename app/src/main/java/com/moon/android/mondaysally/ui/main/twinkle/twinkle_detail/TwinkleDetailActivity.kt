package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import android.view.View.GONE
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.viewpager2.widget.ViewPager2
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTwinkleDetailBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.SallyDialogTwoText
import com.moon.android.mondaysally.ui.main.twinkle.BottomSheetDialogFragment
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleViewModel
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.EDIT_MODE
import me.relex.circleindicator.CircleIndicator3
import org.koin.androidx.viewmodel.ext.android.viewModel


class TwinkleDetailActivity : BaseActivity<ActivityTwinkleDetailBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()
    lateinit var context: Context

    lateinit var imageViewPager: ViewPager2
    lateinit var indicator: CircleIndicator3
    lateinit var viewPagerAdapter: ImageViewpagerAdapter
    lateinit var commentAdapter: CommentAdapter

    override fun getLayoutResId() = R.layout.activity_twinkle_detail

    private val twinkleEditActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
            }
        }

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel

        imageViewPager = binding.activityTwinkleDetailVp2

        twinkleViewModel.hideKeyboard.observe(this, { hideKeyboard ->
            if (hideKeyboard)
                hideKeyboard(binding.activityShopDetailEtComment)
        })

        twinkleViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity) {
                val intent = Intent(context, TwinkleDetailActivity::class.java).apply {
                    putExtra(
                        "position",
                        intent.getIntExtra("position", 0)
                    )
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        twinkleViewModel.bottomSheetOpen.observe(this, { bottomSheetOpen ->
            if (bottomSheetOpen) {
                val bottomDialogFragment = BottomSheetDialogFragment()
                bottomDialogFragment.setOnEditClickListener {
                    val intent = Intent(context, TwinklePostActivity::class.java)
                    intent.putExtra("twinkleResult", twinkleViewModel.twinkleResult.value)
                    intent.putExtra("mode", EDIT_MODE)
                    intent.putExtra("idx", twinkleViewModel.twinkleIndex.value!!)
                    intent.putExtra("name", twinkleViewModel.twinkleResult.value?.giftName)
                    intent.putExtra("usedClover", twinkleViewModel.twinkleResult.value?.option)
                    twinkleEditActivityLauncher.launch(intent)
                    bottomDialogFragment.dismiss()
                }
                bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
                twinkleViewModel.bottomSheetOpen.value = false
            }
        })

        twinkleViewModel.likePostSuccess.observe(this, { likePostSuccess ->
            if (likePostSuccess) {
                heartImageChange(
                    binding.activityShopDetailIvHeart,
                    binding.activityShopDetailTvLike
                )
                animateHeart(binding.activityShopDetailIvHeart)
            }
        })

        twinkleViewModel.commentPostSuccess.observe(this, { commentPostSuccess ->
            if (commentPostSuccess) {
                //ReLoading ?
                twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
                twinkleViewModel.editTextCommentString.set("")
                binding.activityShopDetailEtComment.clearFocus()
                twinkleViewModel.commentPostSuccess.value = false
            }
        })

        twinkleViewModel.commentDeleteSuccess.observe(this, { deletedPosition ->
            commentAdapter.items.removeAt(deletedPosition)
            commentAdapter.notifyItemRemoved(deletedPosition)
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
                binding.activityTwinkleScrollView.smoothScrollToView(binding.activityShopDetailRvComment)
            }
            bindTwinkleHeart(binding.activityShopDetailIvHeart, twinkleResult.isHearted)
        })

        twinkleViewModel.fail.observe(this, { fail ->
//            341	"???????????? ?????? ??????????????????."
//            346	"?????? ????????? ?????????????????????."
//            353	"????????? ????????? ???????????? ?????? ??????????????? ????????????."
//            370	"???????????? ?????? ???????????????."
//            377	"????????? ???????????????."
//            388	"JWT????????? ??????????????????."
//            389	"???????????? ?????? JWT???????????????."
//            404	"???????????? ????????? ??????????????????."
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
        commentAdapter = CommentAdapter()
        commentAdapter.setCommentDeleteClickListener { twinkleComment, position ->
            showSallyDialogTwoText(
                this,
                getString(R.string.comment_delete_check),
                getString(R.string.comment_delete_check_guide),
                getString(R.string.ok),
                object : SallyDialogTwoText.DialogClickListener {
                    override fun onOKClicked() {
                        twinkleViewModel.deleteComment(twinkleComment.idx, position)
                    }
                })
        }
        commentAdapter.setCommentEditClickListener { twinkleComment, position ->

        }
        binding.activityShopDetailRvComment.adapter = commentAdapter
        twinkleViewModel.twinkleIndex.value = intent.getIntExtra("idx", 0)
        twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
    }

    /*
    ScrollView??? ?????? ?????? y ????????? ?????????.
    view??? ?????? ?????? y????????? ?????????.
    2?????? ?????? y????????? ????????? ???????????? ?????????, ?????? ???????????? ????????? ???????????? ?????????. ????????? ????????? ?????? ???????????? ????????? scrollY??? ????????????.
    ????????? ??? ?????? ?????? ????????? ScrollView??? ???????????? view??? ????????? ????????? ????????? ????????? ????????????.
     */
    fun NestedScrollView.smoothScrollToView(
        view: View,
    ) {
        // ???????????? ????????? ??????.
        if (this.getChildAt(0).height <= this.height) return

//        val y = computeDistanceToView(view)
        // (????????? ???????????? ?????? - ?????? ????????? ??? ??????) / (????????? ????????? ?????? - ????????? ?????? ??????) - ??????
//        val ratio = kotlin.math.abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()

        ObjectAnimator.ofInt(this, "scrollY", view.bottom).apply {
            duration = 1000L
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
//                TODO() ??????????????? ??????
            }
            start()
        }
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

    @SuppressLint("SetTextI18n")
    private fun heartImageChange(
        imageView: ImageView,
        textView: TextView
    ) {
        if (twinkleViewModel.twinkleResult.value?.isHearted == "Y") {
            imageView.setImageResource(R.drawable.ic_like_off_gray)
            twinkleViewModel.twinkleResult.value?.isHearted = "N"
            twinkleViewModel.twinkleResult.value?.likenum =
                twinkleViewModel.twinkleResult.value?.likenum?.minus(1)!!
            textView.text = "????????? ${twinkleViewModel.twinkleResult.value?.likenum}???"
        } else {
            vibrate()
            imageView.setImageResource(R.drawable.ic_like_on_orange)
            twinkleViewModel.twinkleResult.value?.isHearted = "Y"
            twinkleViewModel.twinkleResult.value?.likenum =
                twinkleViewModel.twinkleResult.value?.likenum?.plus(1)!!
            textView.text = "????????? ${twinkleViewModel.twinkleResult.value?.likenum}???"
        }

    }

    private fun animateHeart(view: ImageView) {
        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction {
            view.scaleX = 1f
            view.scaleY = 1f
        }.start()
    }

    fun bindTwinkleHeart(imageView: ImageView, isHearted: String) {
        if (isHearted == "Y")
            imageView.setImageResource(R.drawable.ic_like_on_orange)
        else
            imageView.setImageResource(R.drawable.ic_like_off_gray)
    }

    override fun onBackPressed() {
        setIntentResult()
        finish()
    }

    private fun setIntentResult() {
        val intent =
            Intent(context, TwinkleDetailActivity::class.java).apply {
                putExtra(
                    "position",
                    intent.getIntExtra("position", 0)
                )
                putExtra(
                    "isHearted",
                    twinkleViewModel.twinkleResult.value?.isHearted
                )
                putExtra(
                    "likenum",
                    twinkleViewModel.twinkleResult.value?.likenum
                )
            }
        setResult(RESULT_OK, intent)
    }
}