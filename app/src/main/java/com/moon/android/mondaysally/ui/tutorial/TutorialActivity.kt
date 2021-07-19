package com.moon.android.mondaysally.ui.tutorial

import androidx.annotation.LayoutRes
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTutorialBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class TutorialActivity : BaseActivity<ActivityTutorialBinding>() {

    private val tutorialViewModel: TutorialViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_tutorial

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = tutorialViewModel
        val titles = listOf(
            getString(R.string.tutorial_title_1),
            getString(R.string.tutorial_title_2),
            getString(R.string.tutorial_title_1)
        )
        val contents = listOf(
            getString(R.string.tutorial_content_1),
            getString(R.string.tutorial_content_2),
            getString(R.string.tutorial_content_3)
        )

        val viewPagerTutorialFragment = binding.activityTutorialVp2
        val indicator = binding.activityTutorialIndicator
        val adapterViewPager = ViewPagerAdapter(this, titles, contents)

        viewPagerTutorialFragment.adapter = adapterViewPager
        indicator.setViewPager(viewPagerTutorialFragment)
        indicator.createIndicators(3, 0)

        viewPagerTutorialFragment.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    viewPagerTutorialFragment.currentItem = position
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position % 3)
                if (position == 2)  binding.activityTutorialBtnSkip.text = getString(R.string.tutorial_start)
                else binding.activityTutorialBtnSkip.text = getString(R.string.tutorial_skip)
            }
        })

    }

    override fun initAfterBinding() {
//        tutorialViewModel.noMoreTutorial()
    }

    override fun onBackPressed() {
        if (binding.activityTutorialVp2.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.activityTutorialVp2.currentItem = binding.activityTutorialVp2.currentItem - 1
        }
    }
}