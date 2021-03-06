package com.moon.android.mondaysally.ui.main.gift.shop_detail

import android.content.Context
import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityShopDetailBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.SallyDialog
import com.moon.android.mondaysally.ui.main.gift.GiftViewModel
import com.moon.android.mondaysally.ui.main.gift.shop_apply_done.ShopApplyDoneActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopDetailActivity : BaseActivity<ActivityShopDetailBinding>() {

    private val giftViewModel: GiftViewModel by viewModel()
    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_shop_detail

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = giftViewModel
        setTagView()

        giftViewModel.showDialog.observe(this, { showDialog ->
            if (showDialog) {
                showSallyDialog(
                    this,
                    getString(R.string.shop_apply_check),
                    getString(R.string.ok),
                    object : SallyDialog.DialogClickListener {
                        override fun onOKClicked() {
                            giftViewModel.postGift()
                        }
                    })
            }
        })

        giftViewModel.twinkleIndex.observe(this, { twinkleIndex ->
            val intent = Intent(this, ShopApplyDoneActivity::class.java)
            intent.putExtra("idx", twinkleIndex)
            intent.putExtra("name", giftViewModel.giftResult.value?.name)
            intent.putExtra("usedClover", giftViewModel.giftOption.value?.usedClover)
            startActivity(intent)
        })

        giftViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        giftViewModel.giftResult.observe(this, {
            binding.activityShopDetailTagOption.setOption(giftViewModel.giftResult.value?.options)
        })

        giftViewModel.optionIndex.observe(this, { optionIndex ->
            giftViewModel.isOptionSelected.value = true
            optionTagViewOff(optionIndex)
            optionTagViewOn(optionIndex)
        })

        binding.activityShopDetailTagOption.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
                giftViewModel.giftOption.value =
                    giftViewModel.giftResult.value?.options?.get(position)
                giftViewModel.optionIndex.value = position
            }

            override fun onTagLongClick(position: Int, text: String?) {
                giftViewModel.giftOption.value =
                    giftViewModel.giftResult.value?.options?.get(position)
                giftViewModel.optionIndex.value = position
            }

            override fun onSelectedTagDrag(position: Int, text: String?) {
                giftViewModel.giftOption.value =
                    giftViewModel.giftResult.value?.options?.get(position)
                giftViewModel.optionIndex.value = position
            }

            override fun onTagCrossClick(position: Int) {
                giftViewModel.giftOption.value =
                    giftViewModel.giftResult.value?.options?.get(position)
                giftViewModel.optionIndex.value = position
            }
        })

        giftViewModel.fail.observe(this, { fail ->
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
        giftViewModel.giftIndex.value = intent.getIntExtra("idx", 0)
        giftViewModel.getGiftDetail(intent.getIntExtra("idx", 0))
    }

    private fun optionTagViewOff(position: Int) {
        binding.activityShopDetailTagOption.setOption(giftViewModel.giftResult.value?.options)
        giftViewModel.giftResult.value?.options?.forEachIndexed { index, option ->
//            if (index != position) {
            binding.activityShopDetailTagOption.getTagView(index)
                .setTagBorderColor(getColor(R.color.option_border_gray))
            binding.activityShopDetailTagOption.getTagView(index)
                .setTagTextColor(getColor(R.color.option_border_gray))
            binding.activityShopDetailTagOption.getTagView(index)
                .setTypeface(ResourcesCompat.getFont(context, R.font.roboto_light))
//            }
        }
    }

    private fun optionTagViewOn(position: Int) {
        binding.activityShopDetailTagOption.getTagView(position)
            .setTagBorderColor(getColor(R.color.pinkish_orange))
        binding.activityShopDetailTagOption.getTagView(position)
            .setTagTextColor(getColor(R.color.pinkish_orange))
        binding.activityShopDetailTagOption.getTagView(position)
            .setTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
    }

    private fun setTagView() {
        binding.activityShopDetailTagOption.borderColor = getColor(R.color.translate)
        binding.activityShopDetailTagOption.backgroundColor = getColor(R.color.translate)
        binding.activityShopDetailTagOption.tagBorderRadius = 8.0F
        binding.activityShopDetailTagOption.tagTextColor = getColor(R.color.text_dim_gray)
        binding.activityShopDetailTagOption.tagBorderColor = getColor(R.color.option_border_gray)
        binding.activityShopDetailTagOption.borderWidth = 1 * resources.displayMetrics.density
        binding.activityShopDetailTagOption.tagBackgroundColor = getColor(R.color.white)
        binding.activityShopDetailTagOption.tagTypeface =
            ResourcesCompat.getFont(this, R.font.roboto_light)
        binding.activityShopDetailTagOption.tagTextSize =
            13 * resources.displayMetrics.scaledDensity
    }
}