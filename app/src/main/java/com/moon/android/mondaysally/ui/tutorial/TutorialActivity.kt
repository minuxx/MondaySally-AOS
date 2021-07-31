package com.moon.android.mondaysally.ui.tutorial

import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTutorialBinding
import com.moon.android.mondaysally.ui.BaseActivity
import me.relex.circleindicator.CircleIndicator3
import org.koin.androidx.viewmodel.ext.android.viewModel


class TutorialActivity : BaseActivity<ActivityTutorialBinding>() {

    private val tutorialViewModel: TutorialViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_tutorial

    lateinit var viewPagerTutorialFragment: ViewPager2
    lateinit var indicator: CircleIndicator3
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var btnNext: Button

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = tutorialViewModel
        val titles = listOf(
            getString(R.string.tutorial_title_1),
            getString(R.string.tutorial_title_2),
            getString(R.string.tutorial_title_3)
        )
        val contents = listOf(
            getString(R.string.tutorial_content_1),
            getString(R.string.tutorial_content_2),
            getString(R.string.tutorial_content_3)
        )

        viewPagerTutorialFragment = binding.activityTutorialVp2
        indicator = binding.activityTutorialIndicator
        btnNext = binding.activityTutorialBtnSkip
        viewPagerAdapter = ViewPagerAdapter(this, titles, contents)

        viewPagerTutorialFragment.adapter = viewPagerAdapter
        indicator.setViewPager(viewPagerTutorialFragment)
        indicator.createIndicators(3, 0)

        tutorialViewModel.pageNumber.observe(this, { pageNumber ->
            if (pageNumber == 2) binding.activityTutorialBtnSkip.text =
                getString(R.string.tutorial_start)
            else binding.activityTutorialBtnSkip.text = getString(R.string.tutorial_skip)
        })

        tutorialViewModel.exitTutorial.observe(this, { exitTutorial ->
            if (exitTutorial) finish()
        })

        tutorialViewModel.goLastPage.observe(this, { goLastPage ->
            if (goLastPage) viewPagerTutorialFragment.setCurrentItem(2, true)
        })
    }

    override fun initAfterBinding() {
//        일단 테스트해야하므로 주석
//        tutorialViewModel.noMoreTutorial()

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
                tutorialViewModel.whenPageChanged(position)
                indicator.animatePageSelected(position % 3)
            }
        })
    }

    override fun onBackPressed() {
        if (binding.activityTutorialVp2.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.activityTutorialVp2.currentItem = binding.activityTutorialVp2.currentItem - 1
        }
    }
}