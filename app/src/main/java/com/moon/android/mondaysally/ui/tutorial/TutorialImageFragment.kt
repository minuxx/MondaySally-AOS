package com.moon.android.mondaysally.ui.tutorial

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTutorialBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialImageFragment(imageId: Int, title: String, content: String) :
    BaseFragment<FragmentTutorialBinding>() {

    private val tutorialViewModel: TutorialFragmentViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_tutorial

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = tutorialViewModel

    }

    override fun initAfterBinding() {

    }

}