package com.moon.android.mondaysally.ui.tutorial.fragment

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTutorialBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialFragment(val imageId: Int, val title: String, val content: String) :
    BaseFragment<FragmentTutorialBinding>() {

    private val tutorialViewModel: TutorialFragmentViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_tutorial

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = tutorialViewModel

        binding.fragmentTutorialIv.setImageResource(imageId)
        binding.fragmentTutorialTvTitle.text = title
        binding.fragmentTutorialTvContent.text = content

    }

    override fun initAfterBinding() {

    }

}