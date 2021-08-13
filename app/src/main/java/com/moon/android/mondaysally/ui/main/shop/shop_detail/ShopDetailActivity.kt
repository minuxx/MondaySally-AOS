package com.moon.android.mondaysally.ui.main.shop.shop_detail

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityShopDetailBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.SallyDialog
import com.moon.android.mondaysally.ui.main.shop.ShopViewModel
import com.moon.android.mondaysally.ui.main.shop.shop_apply_done.ShopApplyDoneActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopDetailActivity : BaseActivity<ActivityShopDetailBinding>() {

    private val shopViewModel: ShopViewModel by viewModel()
    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_shop_detail

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = shopViewModel
        setTagView()

        shopViewModel.showDialog.observe(this, { showDialog ->
            if (showDialog) {
                showSallyDialog(
                    this,
                    getString(R.string.shop_apply_check),
                    getString(R.string.ok),
                    object : SallyDialog.DialogClickListener {
                        override fun onOKClicked() {
                            shopViewModel.postGift()
                        }
                    })
            }
        })

        shopViewModel.postSuccess.observe(this, { postSuccess ->
            if (postSuccess)
                startNextActivity(ShopApplyDoneActivity::class.java)
        })

        shopViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        shopViewModel.giftResult.observe(this, {
            binding.activityShopDetailTagOption.setOption(shopViewModel.giftResult.value?.options)
        })

        shopViewModel.optionIndex.observe(this, { optionIndex ->
            shopViewModel.isOptionSelected.value = true
            optionTagViewOff(optionIndex)
            optionTagViewOn(optionIndex)
        })

        binding.activityShopDetailTagOption.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
                shopViewModel.giftOption.value =
                    shopViewModel.giftResult.value?.options?.get(position)
                shopViewModel.optionIndex.value = position
            }

            override fun onTagLongClick(position: Int, text: String?) {
                shopViewModel.giftOption.value =
                    shopViewModel.giftResult.value?.options?.get(position)
                shopViewModel.optionIndex.value = position
            }

            override fun onSelectedTagDrag(position: Int, text: String?) {
                shopViewModel.giftOption.value =
                    shopViewModel.giftResult.value?.options?.get(position)
                shopViewModel.optionIndex.value = position
            }

            override fun onTagCrossClick(position: Int) {
                shopViewModel.giftOption.value =
                    shopViewModel.giftResult.value?.options?.get(position)
                shopViewModel.optionIndex.value = position
            }
        })

        shopViewModel.fail.observe(this, { fail ->
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
        shopViewModel.giftIndex.value = intent.getIntExtra("idx", 0)
        shopViewModel.getGiftDetail(intent.getIntExtra("idx", 0))
    }

    private fun optionTagViewOff(position: Int) {
        binding.activityShopDetailTagOption.setOption(shopViewModel.giftResult.value?.options)
        shopViewModel.giftResult.value?.options?.forEachIndexed { index, option ->
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