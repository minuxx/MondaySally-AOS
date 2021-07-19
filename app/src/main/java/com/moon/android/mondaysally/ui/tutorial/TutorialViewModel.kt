package com.moon.android.mondaysally.ui.tutorial

import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.repository.SharedPrefRepository


class TutorialViewModel(private val sharedPrefRepository: SharedPrefRepository) : ViewModel() {

    fun noMoreTutorial() {
        sharedPrefRepository.noMoreTutorial()
    }

}