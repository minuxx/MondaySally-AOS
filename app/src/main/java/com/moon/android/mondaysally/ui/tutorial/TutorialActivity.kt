package com.moon.android.mondaysally.ui.tutorial

import androidx.annotation.LayoutRes
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
        binding.activityTutorialVp2.adapter = ViewPagerAdapter(this, titles, contents)
    }

    override fun initAfterBinding() {
//        splashViewModel.serverVersionCheck()
//        tutorialViewModel.
    }

    override fun onBackPressed() {
        if (binding.activityTutorialVp2.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.activityTutorialVp2.currentItem = binding.activityTutorialVp2.currentItem - 1
        }
    }
}